/*

Copyright (c) 2001-2004 The Regents of the University of California.
All rights reserved.

Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.

GIOTTO_COPYRIGHT_VERSION_2
COPYRIGHTENDKEY
*/

package giotto.giottoc;

import giotto.functionality.table.Host;
import giotto.functionality.table.Task;
import giotto.giottoc.analysis.DepthFirstAdapter;
import giotto.giottoc.ecode.FixupChain;
import giotto.giottoc.ecode.Instruction;
import giotto.giottoc.node.AActualPort;
import giotto.giottoc.node.AActuatorDeclaration;
import giotto.giottoc.node.AActuatorUpdate;
import giotto.giottoc.node.ADeviceDriver;
import giotto.giottoc.node.ADriverDeclaration;
import giotto.giottoc.node.AHostNameIdent;
import giotto.giottoc.node.AModeConnectionAnnotation;
import giotto.giottoc.node.AModeDeclaration;
import giotto.giottoc.node.AModeDeclarationSequence;
import giotto.giottoc.node.AModeSwitch;
import giotto.giottoc.node.AOutputDeclaration;
import giotto.giottoc.node.APortMappingAnnotation;
import giotto.giottoc.node.AProgram;
import giotto.giottoc.node.ASensorDeclaration;
import giotto.giottoc.node.AStatePort;
import giotto.giottoc.node.ATaskAnnotation;
import giotto.giottoc.node.ATaskDeclaration;
import giotto.giottoc.node.ATaskInvocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;

/**

This class implements the e code generation for the embedded machine,
which is the target platform of giottoc.

@author Christoph Kirsch
@version ECode.java,v 1.10 2004/09/29 03:51:56 cxh Exp
@since Giotto 1.0.1
*/
public class ECode extends Wrapper {
    private FTable functionTable;
    private STable scheduleTable;
    private HTable hostTable;

    private boolean pureGiotto;
    private boolean dynamicGiotto;

    private final Hashtable modeUnits = new Hashtable();

    private int modeConnectionPeriod = -1;

    private boolean isThereAModeConnectionAnnotation = false;

    private String hostStartName;
    private int hostStartAddress;

    private String startModeName;
    private int startModeAddress;

    private String currentModeName = null;
    private String currentTaskName = null;

    private Hashtable modeAddresses = null;

    private static final int giottoTriggerIndex = 0;

    public ECode(
            SymbolTable symbolTable,
            FTable functionTable,
            STable scheduleTable,
            HTable hostTable,
            boolean pureGiotto,
            boolean dynamicGiotto) {
        super(symbolTable);

        this.functionTable = functionTable;
        this.scheduleTable = scheduleTable;
        this.hostTable = hostTable;

        this.pureGiotto = pureGiotto;
        this.dynamicGiotto = dynamicGiotto;
    }

    // Action code

    public void inAProgram(AProgram node) {
        node.apply(new ComputeModeUnits());

        if (pureGiotto || symbolTable.hosts.isEmpty()) {
            hostStartName = null;
            hostStartAddress = Instruction.getNumberOf();

            node.apply(new GenECode());
        } else {
            for (int i = 0; i < Host.getNumberOf(); i++) {
                final Host host = Host.getHost(i);
                hostStartName = host.getName();
                hostStartAddress = Instruction.getNumberOf();

                host.addStartAddress(hostStartAddress);

                node.apply(new GenECode());
            }
        }
    }

    private class ComputeModeUnits extends DepthFirstAdapter {
        public void inAModeDeclaration(AModeDeclaration node) {
            ModeUnit modeUnit;

            node.apply(modeUnit = new ModeUnit());

            final String modeName = node.getModeName().getText();

            modeUnits.put(modeName, modeUnit);

            final int nUnits = modeUnit.nUnits;

            if (nUnits > Instruction.getMaxUnits())
                Instruction.setMaxUnits(nUnits);

            final int modePeriod = modeUnit.modePeriod;

            final int unitPeriod = modePeriod / nUnits;

            if (Instruction.getMinUnitPeriod() < 0)
                Instruction.setMinUnitPeriod(unitPeriod);
            else
                Instruction.setMinUnitPeriod(ModeUnit.gcd(unitPeriod, Instruction.getMinUnitPeriod()));
        }

        public void outAModeConnectionAnnotation(AModeConnectionAnnotation node) {
            if (!pureGiotto)
                isThereAModeConnectionAnnotation = true;
        }

        public void outAModeDeclarationSequence(AModeDeclarationSequence node) {
            if (!pureGiotto && isThereAModeConnectionAnnotation) {
                modeConnectionPeriod = Instruction.getMinUnitPeriod() / 5;

                Instruction.setMinUnitPeriod(ModeUnit.gcd(modeConnectionPeriod, Instruction.getMinUnitPeriod()));
            }
        }
    }

    private boolean isEnabled(int frequency, int unit, int nUnits) {
        final int normFrequency = frequency * unit;

        return (normFrequency % nUnits == 0);
    }

    private class GenTaskOutput extends DepthFirstAdapter {
        private ArrayList outputParameters = null;

        private int unit;
        private int nUnits;

        public GenTaskOutput(int unit, int nUnits) {
            this.unit = unit;
            this.nUnits = nUnits;
        }

        private class ComputeTaskOutputPorts extends DepthFirstAdapter {
            public void outAActualPort(AActualPort node) {
                final String outputPortName = node.getPortName().getText();

                outputParameters.add(outputPortName);
            }
        }

        public void inATaskInvocation(ATaskInvocation node) {
            final int frequency = Integer.parseInt(node.getTaskFrequency().getText());

            if (isEnabled(frequency, unit, nUnits)) {
                outputParameters = new ArrayList();

                final String taskName = node.getTaskName().getText();

                final ATaskDeclaration taskDeclaration = (ATaskDeclaration) symbolTable.tasks.get(taskName);

                taskDeclaration.apply(new ComputeTaskOutputPorts());
            }
        }

        public void outATaskInvocation(ATaskInvocation node) {
            if (outputParameters != null) {
                final String taskName = node.getTaskName().getText();

                for (ListIterator outputIterator = outputParameters.listIterator(); outputIterator.hasNext();) {
                    final String outputPortName = (String) outputIterator.next();

                    final AOutputDeclaration outputDeclaration =
                        (AOutputDeclaration) symbolTable.outputPorts.get(outputPortName);

                    final String outputPortType = outputDeclaration.getTypeName().getText();

                    final int driverIndex = getCopyDriverIndex(outputPortName, outputPortType);

                    new Instruction(
                            Instruction.callCode,
                            driverIndex,
                            "Call output port copy driver: "
                            + getCopyDriverWrapperName(outputPortName, outputPortType)
                            + " for task: "
                            + Task.getTask(taskName).getWrapperName());
                }

                outputParameters = null;
            }
        }
    }

    private boolean isPortOnHost(APortMappingAnnotation portMappingAnnotation) {
        if (!pureGiotto && (hostStartName != null) && (portMappingAnnotation != null)) {
            final String hostName = ((AHostNameIdent) portMappingAnnotation.getHostName()).getIdent().getText();

            if (hostName.compareTo(hostStartName) != 0)
                return false;
        }

        return true;
    }

    private class GenActuatorUpdate extends DepthFirstAdapter {
        private int unit;
        private int nUnits;

        public GenActuatorUpdate(int unit, int nUnits) {
            this.unit = unit;
            this.nUnits = nUnits;
        }

        public void outAActuatorUpdate(AActuatorUpdate node) {
            final String actuatorPortName = node.getActuatorPortName().getText();

            final AActuatorDeclaration actuatorDeclaration =
                (AActuatorDeclaration) symbolTable.actuatorPorts.get(actuatorPortName);

            final APortMappingAnnotation portMappingAnnotation =
                (APortMappingAnnotation) actuatorDeclaration.getPortAnnotation();

            if (pureGiotto || isPortOnHost(portMappingAnnotation)) {
                final int frequency = Integer.parseInt(node.getActuatorFrequency().getText());

                if (isEnabled(frequency, unit, nUnits)) {
                    final String driverName = node.getDriverName().getText();

                    final int conditionIndex = getConditionIndex(actuatorPortName, driverName);
                    final int driverIndex = getDriverIndex(actuatorPortName, driverName);

                    final int thenAddress = Instruction.getNumberOf() + 1;
                    final int elseAddress = Instruction.getNumberOf() + 2;

                    new Instruction(
                            Instruction.ifCode,
                            conditionIndex,
                            thenAddress,
                            elseAddress,
                            "If actuator driver: " + getConditionWrapperName(actuatorPortName, driverName));

                    new Instruction(
                            Instruction.callCode,
                            driverIndex,
                            "Call actuator driver: " + getDriverWrapperName(actuatorPortName, driverName));
                }
            }
        }
    }

    private class GenActuatorDeviceUpdate extends DepthFirstAdapter {
        private int unit;
        private int nUnits;

        public GenActuatorDeviceUpdate(int unit, int nUnits) {
            this.unit = unit;
            this.nUnits = nUnits;
        }

        public void outAActuatorUpdate(AActuatorUpdate node) {
            final String actuatorPortName = node.getActuatorPortName().getText();

            final AActuatorDeclaration actuatorDeclaration =
                (AActuatorDeclaration) symbolTable.actuatorPorts.get(actuatorPortName);

            final APortMappingAnnotation portMappingAnnotation =
                (APortMappingAnnotation) actuatorDeclaration.getPortAnnotation();

            if (pureGiotto || isPortOnHost(portMappingAnnotation)) {
                final int frequency = Integer.parseInt(node.getActuatorFrequency().getText());

                if (isEnabled(frequency, unit, nUnits)) {
                    final ADeviceDriver deviceDriver = (ADeviceDriver) actuatorDeclaration.getDeviceDriver();

                    if (deviceDriver != null) {
                        final String deviceDriverName = deviceDriver.getDeviceDriverName().getText();

                        final int driverIndex = getDriverIndex(actuatorPortName, deviceDriverName);

                        new Instruction(
                                Instruction.callCode,
                                driverIndex,
                                "Call actuator device driver: " + getDriverWrapperName(actuatorPortName, deviceDriverName));
                    }
                }
            }
        }
    }

    private boolean isTaskOnHost(ATaskInvocation taskInvocation) {
        if (!pureGiotto && (hostStartName != null)) {
            final ATaskAnnotation taskAnnotation = (ATaskAnnotation) taskInvocation.getTaskAnnotation();

            if (taskAnnotation != null) {
                final String hostName = ((AHostNameIdent) taskAnnotation.getHostName()).getIdent().getText();

                if (hostName.compareTo(hostStartName) != 0)
                    return false;
            }
        }

        return true;
    }

    private class GenSensorDeviceUpdate extends DepthFirstAdapter {
        final private HashSet sensorPorts = new HashSet();

        protected int unit;
        protected int nUnits;

        public GenSensorDeviceUpdate(int unit, int nUnits) {
            this.unit = unit;
            this.nUnits = nUnits;
        }

        protected class ComputeSensorPorts extends DepthFirstAdapter {
            public void outAActualPort(AActualPort node) {
                final String portName = node.getPortName().getText();

                if (symbolTable.sensorPorts.containsKey(portName)) {
                    final ASensorDeclaration sensorDeclaration =
                        (ASensorDeclaration) symbolTable.sensorPorts.get(portName);

                    final APortMappingAnnotation portMappingAnnotation =
                        (APortMappingAnnotation) sensorDeclaration.getPortAnnotation();

                    if (pureGiotto || isPortOnHost(portMappingAnnotation))
                        sensorPorts.add(portName);
                }
            }
        }

        public void outAModeSwitch(AModeSwitch node) {
            final int frequency = Integer.parseInt(node.getModeSwitchFrequency().getText());

            if (isEnabled(frequency, unit, nUnits)) {
                final String driverName = node.getDriverName().getText();

                final ADriverDeclaration driverDeclaration = (ADriverDeclaration) symbolTable.drivers.get(driverName);

                driverDeclaration.apply(new ComputeSensorPorts());
            }
        }

        public void outATaskInvocation(ATaskInvocation node) {
            if (pureGiotto || isTaskOnHost(node)) {
                final int frequency = Integer.parseInt(node.getTaskFrequency().getText());

                if (isEnabled(frequency, unit, nUnits)) {
                    if (node.getDriverName() != null) {
                        final String driverName = node.getDriverName().getText();

                        final ADriverDeclaration driverDeclaration =
                            (ADriverDeclaration) symbolTable.drivers.get(driverName);

                        driverDeclaration.apply(new ComputeSensorPorts());
                    }
                }
            }
        }

        public void outAModeDeclaration(AModeDeclaration node) {
            for (Iterator sensorIterator = sensorPorts.iterator(); sensorIterator.hasNext();) {
                final String sensorPortName = (String) sensorIterator.next();

                final ASensorDeclaration sensorDeclaration =
                    (ASensorDeclaration) symbolTable.sensorPorts.get(sensorPortName);

                final APortMappingAnnotation portMappingAnnotation =
                    (APortMappingAnnotation) sensorDeclaration.getPortAnnotation();

                if (pureGiotto || isPortOnHost(portMappingAnnotation)) {
                    final ADeviceDriver deviceDriver = (ADeviceDriver) sensorDeclaration.getDeviceDriver();

                    if (deviceDriver != null) {
                        final String deviceDriverName = deviceDriver.getDeviceDriverName().getText();

                        final int driverIndex = getDriverIndex(sensorPortName, deviceDriverName);

                        new Instruction(
                                Instruction.callCode,
                                driverIndex,
                                "Call sensor device driver: " + getDriverWrapperName(sensorPortName, deviceDriverName));
                    }
                }
            }
        }
    }

    private class GenSensorDeviceUpdateForModeSwitches extends GenSensorDeviceUpdate {
        public GenSensorDeviceUpdateForModeSwitches(int unit, int nUnits) {
            super(unit, nUnits);
        }

        public void outATaskInvocation(ATaskInvocation node) {
            // Overwrite to empty method because here we are only interested in mode switches
        }
    }

    private class GenSensorDeviceUpdateForTaskInvocations extends GenSensorDeviceUpdate {
        public GenSensorDeviceUpdateForTaskInvocations(int unit, int nUnits) {
            super(unit, nUnits);
        }

        public void outAModeSwitch(AModeSwitch node) {
            // Overwrite to empty method because here we are only interested in task invocations
        }
    }

    private class PreemptedTasksHyperPeriod extends DepthFirstAdapter {
        private int unit;
        private int nUnits;

        public boolean preemptedTasks = false;
        public int hyperPeriod = 1;

        public PreemptedTasksHyperPeriod(String modeName, int unit) {
            this.unit = unit;

            final ModeUnit modeUnit = (ModeUnit) modeUnits.get(modeName);

            this.nUnits = modeUnit.nUnits;
        }

        public void outATaskInvocation(ATaskInvocation node) {
            final int frequency = Integer.parseInt(node.getTaskFrequency().getText());

            if (!isEnabled(frequency, unit, nUnits)) {
                preemptedTasks = true;

                hyperPeriod = ModeUnit.lcm(nUnits / frequency, hyperPeriod);
            }
        }
    }

    private class GenModeSwitches extends DepthFirstAdapter {
        private String modeName;
        private int unit;
        private int nUnits;
        private int modePeriod;

        private PreemptedTasksHyperPeriod preemptedTasksHyperPeriod;

        private int scheduleTime;

        public GenModeSwitches(
                String modeName,
                int unit,
                PreemptedTasksHyperPeriod preemptedTasksHyperPeriod,
                int scheduleTime) {
            this.modeName = modeName;
            this.unit = unit;

            final ModeUnit modeUnit = (ModeUnit) modeUnits.get(modeName);

            this.nUnits = modeUnit.nUnits;
            this.modePeriod = modeUnit.modePeriod;

            this.preemptedTasksHyperPeriod = preemptedTasksHyperPeriod;

            this.scheduleTime = scheduleTime;
        }

        public void outAModeSwitch(AModeSwitch node) {
            final int frequency = Integer.parseInt(node.getModeSwitchFrequency().getText());

            if (isEnabled(frequency, unit, nUnits)) {
                final String targetModeName = node.getModeName().getText();

                int newUnit = 0;
                int newDelta = 0;

                if (!dynamicGiotto || symbolTable.modes.containsKey(targetModeName)) {
                    // In non-dynamic Giotto symbolTable.modes.containsKey(targetModeName) is true
                    // because we checked this in TypeChecker
                    final ModeUnit modeUnit = (ModeUnit) modeUnits.get(targetModeName);

                    final int nTargetUnits = modeUnit.nUnits;
                    final int targetModePeriod = modeUnit.modePeriod;

                    if (unit != 0 && preemptedTasksHyperPeriod.preemptedTasks) {
                        final int hyperPeriod = preemptedTasksHyperPeriod.hyperPeriod;
                        final int delta = (hyperPeriod - (unit % hyperPeriod)) * (modePeriod / nUnits);

                        newDelta = delta % (targetModePeriod / nTargetUnits);
                        newUnit = (nTargetUnits - ((delta - newDelta) * nTargetUnits) / targetModePeriod) % nTargetUnits;

                        // System.out.println("mode: " + modeName + ", unit: " + unit +
                        // ", hyper: " + hyperPeriod + ", delta: " + delta +
                        // ", newDelta: " + newDelta + ", newUnit: " + newUnit);
                    }
                }

                String declaredModeName;

                if (dynamicGiotto && !symbolTable.modes.containsKey(targetModeName))
                    declaredModeName = "";
                else
                    declaredModeName = targetModeName;

                final String driverName = node.getDriverName().getText();

                final int conditionIndex = getConditionIndex(declaredModeName, driverName);
                final int driverIndex = getDriverIndex(declaredModeName, driverName);

                final int ifAddress = Instruction.getNumberOf();
                final int thenAddress = ifAddress + 1;

                int elseAddress = ifAddress + 3;

                if (newDelta > 0)
                    elseAddress++;

                new Instruction(
                        Instruction.ifCode,
                        conditionIndex,
                        thenAddress,
                        elseAddress,
                        "If mode driver: " + getConditionWrapperName(declaredModeName, driverName));

                new Instruction(
                        Instruction.callCode,
                        driverIndex,
                        "Call mode driver: " + getDriverWrapperName(declaredModeName, driverName));

                final String unitModeName = targetModeName + Integer.toString(newUnit);

                if (newDelta > 0) {
                    final int futureAddress = ifAddress + 2;

                    // -1 indicates end of fixup chain
                    int modeAddress = -1;

                    if (modeAddresses.containsKey(unitModeName)) {
                        final FixupChain fixupChain = (FixupChain) modeAddresses.get(unitModeName);
                        modeAddress = fixupChain.getAddress();

                        if (modeAddress < 0)
                            // Negative addresses need fixup
                            // We add 2 in order to be able to handle 'jumpAddress == 0' and 'jumpAddress == 1'
                            modeAddresses.put(unitModeName, new FixupChain(- (futureAddress + 2), newUnit, targetModeName));
                    } else
                        modeAddresses.put(unitModeName, new FixupChain(- (futureAddress + 2), newUnit, targetModeName));

                    if (newDelta - scheduleTime < 0)
                        throw new RuntimeException(
                                "Mode switch from mode: "
                                + modeName
                                + ", unit: "
                                + unit
                                + " to mode: "
                                + targetModeName
                                + ", unit: "
                                + newUnit
                                + " too much delayed.");

                    new Instruction(
                            Instruction.futureCode,
                            giottoTriggerIndex,
                            modeAddress,
                            newDelta - scheduleTime,
                            "Triggered jump from mode: "
                            + modeName
                            + ", unit: "
                            + unit
                            + " to mode: "
                            + targetModeName
                            + ", unit: "
                            + newUnit);

                    // Generate return

                    new Instruction(
                            Instruction.returnCode,
                            "From delayed mode switch in mode: " + modeName + ", unit: " + unit);
                } else {
                    final int jumpAddress = ifAddress + 2;

                    // -1 indicates end of fixup chain
                    int taskInvocationAddress = -1;

                    if (Instruction.hasTaskInvocationAddresses(unitModeName)) {
                        final FixupChain fixupChain = Instruction.getTaskInvocationAddresses(unitModeName);
                        taskInvocationAddress = fixupChain.getAddress();

                        if (taskInvocationAddress < 0)
                            // Negative addresses need fixup
                            // We add 2 in order to be able to handle 'jumpAddress == 0' and 'jumpAddress == 1'
                            Instruction.addTaskInvocationAddresses(
                                    unitModeName,
                                    new FixupChain(- (jumpAddress + 2), newUnit, targetModeName));
                    } else
                        Instruction.addTaskInvocationAddresses(
                                unitModeName,
                                new FixupChain(- (jumpAddress + 2), newUnit, targetModeName));

                    new Instruction(
                            Instruction.jumpCode,
                            taskInvocationAddress,
                            " Switch from mode: "
                            + modeName
                            + ", unit: "
                            + unit
                            + " to mode: "
                            + targetModeName
                            + ", unit: "
                            + newUnit);
                }
            }
        }
    }

    private class GenTaskSchedule extends DepthFirstAdapter {
        private int unit;
        private int nUnits;
        private int modePeriod;

        private int releaseTime;

        public GenTaskSchedule(int unit, int nUnits, int modePeriod, int releaseTime) {
            this.unit = unit;
            this.nUnits = nUnits;
            this.modePeriod = modePeriod;

            this.releaseTime = releaseTime;
        }

        public void outATaskInvocation(ATaskInvocation node) {
            if (pureGiotto || isTaskOnHost(node)) {
                final int frequency = Integer.parseInt(node.getTaskFrequency().getText());

                if (isEnabled(frequency, unit, nUnits)) {
                    final String taskName = node.getTaskName().getText();

                    final ATaskAnnotation taskAnnotation = (ATaskAnnotation) node.getTaskAnnotation();

                    boolean isTaskAnnotated = false;

                    if (!pureGiotto && (taskAnnotation != null))
                        if (taskAnnotation.getScheduleConnectionList() != null)
                            isTaskAnnotated = true;

                    if (node.getDriverName() != null) {
                        final String driverName = node.getDriverName().getText();

                        final int conditionIndex = getConditionIndex(taskName, driverName);
                        final int driverIndex = getDriverIndex(taskName, driverName);

                        final int thenAddress = Instruction.getNumberOf() + 1;
                        int elseAddress = Instruction.getNumberOf() + 3;

                        if (isTaskAnnotated)
                            // Skip schedule insruction for message task generated below
                            elseAddress++;

                        new Instruction(
                                Instruction.ifCode,
                                conditionIndex,
                                thenAddress,
                                elseAddress,
                                "If task driver: " + getConditionWrapperName(taskName, driverName));

                        new Instruction(
                                Instruction.callCode,
                                driverIndex,
                                "Call task driver: " + getDriverWrapperName(taskName, driverName));
                    }

                    int relativeDeadline = modePeriod / frequency - releaseTime;

                    // Task annotation

                    if (isTaskAnnotated) {
                        final int messageIndex = getMessageIndex(currentModeName, taskName);

                        final int releaseMessageTime = relativeDeadline >> 1;

                        new Instruction(
                                Instruction.scheduleCode,
                                messageIndex,
                                0,
                                releaseMessageTime + (relativeDeadline << 12),
                                "Schedule message: "
                                + getMessageWrapperName(currentModeName, taskName)
                                + ", release time: "
                                + releaseMessageTime
                                + ", relative deadline: "
                                + relativeDeadline);

                        Instruction.setMinUnitPeriod(ModeUnit.gcd(releaseMessageTime, Instruction.getMinUnitPeriod()));

                        relativeDeadline = releaseMessageTime;
                    }

                    // Schedule task

                    final int taskIndex = Task.getTask(taskName).getIndex();

                    // FIXME: Trivial release time: 0, only one annotation: index 0

                    new Instruction(
                            Instruction.scheduleCode,
                            taskIndex,
                            0,
                            0 + (relativeDeadline << 12),
                            "Schedule task: "
                            + Task.getTask(taskName).getWrapperName()
                            + ", release time: "
                            + "0"
                            + ", relative deadline: "
                            + relativeDeadline);
                }
            }
        }
    }

    private class GenECode extends DepthFirstAdapter {
        public void outAOutputDeclaration(AOutputDeclaration node) {
            final String outputPortName = node.getPortName().getText();
            final String initializationDriverName = node.getInitializationDriver().getText();

            final int driverIndex = getInitOutputDriverIndex(outputPortName, initializationDriverName);

            new Instruction(
                    Instruction.callCode,
                    driverIndex,
                    "Call initialization driver: "
                    + getInitOutputDriverWrapperName(outputPortName, initializationDriverName));
        }

        public void inATaskDeclaration(ATaskDeclaration node) {
            currentTaskName = node.getTaskName().getText();
        }

        public void outAStatePort(AStatePort node) {
            final String statePortName = node.getPortName().getText();
            final String initializationDriverName = node.getInitializationDriver().getText();

            final int driverIndex =
                getInitStateDriverIndex(currentTaskName, statePortName, initializationDriverName);

            new Instruction(
                    Instruction.callCode,
                    driverIndex,
                    "Call initialization driver: "
                    + getInitStateDriverWrapperName(currentTaskName, statePortName, initializationDriverName));
        }

        public void outATaskDeclaration(ATaskDeclaration node) {
            currentTaskName = null;
        }

        public void inAModeDeclarationSequence(AModeDeclarationSequence node) {
            final String modeName = node.getModeName().getText();

            startModeName = modeName;

            final int jumpAddress = Instruction.getNumberOf();

            // -1 indicates end of fixup chain
            int modeAddress = -1;

            startModeAddress = - (jumpAddress + 2);

            new Instruction(Instruction.jumpCode, modeAddress, "Jump to start mode: " + startModeName);

            modeAddresses = new Hashtable();

            // FIXME: need more to maintain fixup chains of e code for each host
            Instruction.resetTaskInvocationAddresses();
        }

        public void inAModeDeclaration(AModeDeclaration node) {
            currentModeName = node.getModeName().getText();
        }

        private boolean isAnnotationEnabledForHost(AModeConnectionAnnotation modeConnectionAnnotation) {
            if (!pureGiotto && (hostStartName != null)) {
                final String hostName =
                    ((AHostNameIdent) modeConnectionAnnotation.getHostName()).getIdent().getText();

                if (hostName.compareTo(hostStartName) == 0)
                    return true;
            }

            return false;
        }

        public void outAModeDeclaration(AModeDeclaration node) {
            final String modeName = node.getModeName().getText();
            final ModeUnit modeUnit = (ModeUnit) modeUnits.get(modeName);

            final int modePeriod = modeUnit.modePeriod;
            final int nUnits = modeUnit.nUnits;
            final int unitPeriod = modePeriod / nUnits;

            final int modeAddressZero = Instruction.getNumberOf();

            if (modeName.compareTo(startModeName) == 0)
                Instruction.get(- (startModeAddress + 2)).fixupJump(modeAddressZero);

            for (int unit = 0; unit < nUnits; unit++) {
                // Fixup of mode switch futures (only necessary for non-harmonic mode switching)
                final String unitModeName = modeName + Integer.toString(unit);

                final int modeAddress = Instruction.getNumberOf();

                if (modeAddresses.containsKey(unitModeName)) {
                    final FixupChain fixupChain = (FixupChain) modeAddresses.get(unitModeName);
                    final int fixupModeAddress = fixupChain.getAddress();

                    if (fixupModeAddress < -1) {
                        Instruction.get(- (fixupModeAddress + 2)).fixupFuture(modeAddress);

                        modeAddresses.put(unitModeName, new FixupChain(modeAddress, unit, modeName));
                    } else if (fixupModeAddress == -1)
                        throw new RuntimeException("Fixup error.");
                    else
                        throw new RuntimeException("Ambiguous mode name declaration.");
                } else
                    modeAddresses.put(unitModeName, new FixupChain(modeAddress, unit, modeName));

                // Generate task output port update calls
                node.apply(new GenTaskOutput(unit, nUnits));

                // Generate actuator update calls
                node.apply(new GenActuatorUpdate(unit, nUnits));

                // Generate actuator device driver calls
                node.apply(new GenActuatorDeviceUpdate(unit, nUnits));

                // Generate sensor device driver calls
                if (pureGiotto)
                    node.apply(new GenSensorDeviceUpdateForModeSwitches(unit, nUnits));
                else
                    // FIXME: Updating here all sensor ports even for task invocations
                    // may be wrong if next mode has different task invocations reading different sensor ports
                    node.apply(new GenSensorDeviceUpdate(unit, nUnits));

                if (!pureGiotto && (node.getModeConnectionAnnotation() != null)) {
                    final AModeConnectionAnnotation modeConnectionAnnotation =
                        (AModeConnectionAnnotation) node.getModeConnectionAnnotation();

                    if (modeConnectionAnnotation.getScheduleConnectionList() != null) {
                        if (isAnnotationEnabledForHost(modeConnectionAnnotation)) {
                            final int messageIndex = Task.getTask(currentModeName).getIndex();

                            new Instruction(
                                    Instruction.scheduleCode,
                                    messageIndex,
                                    0,
                                    0 + (modeConnectionPeriod << 12),
                                    "Schedule message: "
                                    + Task.getTask(currentModeName).getWrapperName()
                                    + ", release time: "
                                    + "0"
                                    + ", relative deadline: "
                                    + modeConnectionPeriod);
                        }
                    }
                }

                int scheduleTime;

                if (!pureGiotto && isThereAModeConnectionAnnotation) {
                    final int modeSwitchesAddress = Instruction.getNumberOf() + 2;

                    new Instruction(
                            Instruction.futureCode,
                            giottoTriggerIndex,
                            modeSwitchesAddress,
                            modeConnectionPeriod,
                            "Triggered jump to mode switches in mode: " + modeName + ", unit: " + unit);

                    // Generate return

                    new Instruction(
                            Instruction.returnCode,
                            "From output, actuator, sensor update in mode: " + modeName + ", unit: " + unit);

                    scheduleTime = modeConnectionPeriod;
                } else
                    scheduleTime = 0;

                // Compute hyper period of preempted tasks
                PreemptedTasksHyperPeriod preemptedTasksHyperPeriod;

                node.apply(preemptedTasksHyperPeriod = new PreemptedTasksHyperPeriod(modeName, unit));

                // Generate mode switch checks
                node.apply(new GenModeSwitches(modeName, unit, preemptedTasksHyperPeriod, scheduleTime));

                // Fixup of mode switch jumps
                final int taskInvocationAddress = Instruction.getNumberOf();

                if (Instruction.hasTaskInvocationAddresses(unitModeName)) {
                    final FixupChain fixupChain = Instruction.getTaskInvocationAddresses(unitModeName);
                    final int fixupTaskInvocationAddress = fixupChain.getAddress();

                    if (fixupTaskInvocationAddress < -1) {
                        Instruction.get(- (fixupTaskInvocationAddress + 2)).fixupJump(taskInvocationAddress);

                        Instruction.addTaskInvocationAddresses(
                                unitModeName,
                                new FixupChain(taskInvocationAddress, unit, modeName));
                    } else if (fixupTaskInvocationAddress == -1)
                        throw new RuntimeException("Fixup error.");
                    else
                        throw new RuntimeException("Ambiguous mode name declaration.");
                } else
                    Instruction.addTaskInvocationAddresses(
                            unitModeName,
                            new FixupChain(taskInvocationAddress, unit, modeName));

                // Generate sensor device driver calls for sensor ports read by tasks
                if (pureGiotto)
                    node.apply(new GenSensorDeviceUpdateForTaskInvocations(unit, nUnits));

                // Generate task scheduling
                node.apply(new GenTaskSchedule(unit, nUnits, modePeriod, scheduleTime));

                // Generate jump to next unit
                if (unit == nUnits - 1)
                    new Instruction(
                            Instruction.futureCode,
                            giottoTriggerIndex,
                            modeAddressZero,
                            unitPeriod - scheduleTime,
                            "Triggered jump to mode: " + modeName + ", unit: 0");
                else {
                    int nextUnitAddress = Instruction.getNumberOf() + 2;

                    new Instruction(
                            Instruction.futureCode,
                            giottoTriggerIndex,
                            nextUnitAddress,
                            unitPeriod - scheduleTime,
                            "Triggered jump to mode: " + modeName + ", unit: " + Integer.toString(unit + 1));
                }

                // Generate return
                new Instruction(Instruction.returnCode, "From mode: " + modeName + ", unit: " + unit);
            }

            currentModeName = null;
        }
    }

}
