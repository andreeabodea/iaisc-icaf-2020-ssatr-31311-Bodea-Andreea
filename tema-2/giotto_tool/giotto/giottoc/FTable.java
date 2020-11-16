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

import giotto.functionality.table.Condition;
import giotto.functionality.table.Driver;
import giotto.functionality.table.Port;
import giotto.functionality.table.Task;
import giotto.functionality.table.Trigger;
import giotto.giottoc.analysis.DepthFirstAdapter;
import giotto.giottoc.node.AActualGlobalParameter;
import giotto.giottoc.node.AActualPort;
import giotto.giottoc.node.AActualScopedParameter;
import giotto.giottoc.node.AActuatorDeclaration;
import giotto.giottoc.node.AActuatorUpdate;
import giotto.giottoc.node.ACallDriver;
import giotto.giottoc.node.ADeviceDriver;
import giotto.giottoc.node.ADriverDeclaration;
import giotto.giottoc.node.AFormalPort;
import giotto.giottoc.node.AModeConnectionAnnotation;
import giotto.giottoc.node.AModeDeclaration;
import giotto.giottoc.node.AModeDeclarationSequence;
import giotto.giottoc.node.AModeSwitch;
import giotto.giottoc.node.AOutputDeclaration;
import giotto.giottoc.node.AScheduleConnectionList;
import giotto.giottoc.node.AScheduleTask;
import giotto.giottoc.node.ASensorDeclaration;
import giotto.giottoc.node.AStatePort;
import giotto.giottoc.node.ATaskAnnotation;
import giotto.giottoc.node.ATaskDeclaration;
import giotto.giottoc.node.ATaskInvocation;
import giotto.giottoc.node.PActualScopedParameters;
import giotto.giottoc.node.PGuardParameters;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

/**

This class implements code generation of the functionality wrappers.
A functionality wrapper is a procedure without arguments.  The target
machine will invoke and schedule wrappers.

@author Christoph Kirsch
@version FTable.java,v 1.8 2004/09/29 03:52:02 cxh Exp
@since Giotto 1.0.1
*/
public class FTable extends Wrapper {
    private boolean pureGiotto;
    private boolean dynamicGiotto;
    private boolean simulinkGiotto;

    private String currentModeName = null;

    private Task currentTask = null;

    private final Hashtable modeOutputParameters = new Hashtable();
    private final Hashtable taskInputParameters = new Hashtable();
    private final Hashtable driverDestinationParameters = new Hashtable();

    public FTable(SymbolTable symbolTable, boolean pureGiotto, boolean dynamicGiotto, boolean simulinkGiotto) {
        super(symbolTable);

        this.pureGiotto = pureGiotto;
        this.dynamicGiotto = dynamicGiotto;
        this.simulinkGiotto = simulinkGiotto;

        new Trigger("timer", "giotto_timer");
    }

    // Action code

    public void inASensorDeclaration(ASensorDeclaration node) {
        final String sensorPortName = node.getPortName().getText();
        final String sensorPortType = node.getTypeName().getText();

        if (!Port.isDeclared(sensorPortName))
            new Port(sensorPortName, sensorPortType);
        else
            throw new RuntimeException("Ambiguous sensor port name.");

        final ADeviceDriver deviceDriver = (ADeviceDriver) node.getDeviceDriver();

        if (deviceDriver != null) {
            final String deviceDriverName = deviceDriver.getDeviceDriverName().getText();
            final String generatedDriverName = generateName(sensorPortName, deviceDriverName);

            if (!Driver.isDeclared(generatedDriverName)) {
                final Driver driver = new Driver(generatedDriverName, deviceDriverName, 0);

                driver.addParameter(Port.getPort(sensorPortName));
            } else
                throw new RuntimeException("Ambiguous sensor device driver name.");
        }
    }

    public void inAActuatorDeclaration(AActuatorDeclaration node) {
        final String actuatorPortName = node.getPortName().getText();
        final String actuatorPortType = node.getTypeName().getText();

        if (!Port.isDeclared(actuatorPortName))
            new Port(actuatorPortName, actuatorPortType);
        else
            throw new RuntimeException("Ambiguous actuator port name.");

        final ADeviceDriver deviceDriver = (ADeviceDriver) node.getDeviceDriver();

        if (deviceDriver != null) {
            final String deviceDriverName = deviceDriver.getDeviceDriverName().getText();
            final String generatedDriverName = generateName(actuatorPortName, deviceDriverName);

            if (!Driver.isDeclared(generatedDriverName)) {
                final Driver driver = new Driver(generatedDriverName, deviceDriverName, 0);

                driver.addParameter(Port.getPort(actuatorPortName));
            } else
                throw new RuntimeException("Ambiguous actuator device driver name.");
        }
    }

    public void inAOutputDeclaration(AOutputDeclaration node) {
        final String outputPortName = node.getPortName().getText();
        final String outputPortType = node.getTypeName().getText();

        final String generatedGlobalPortName = generateGlobalPortName(outputPortName);

        if (!Port.isDeclared(generatedGlobalPortName))
            new Port(generatedGlobalPortName, outputPortType);
        else
            throw new RuntimeException("Ambiguous global output port name.");

        final String generatedLocalPortName = generateLocalPortName(outputPortName);

        if (!Port.isDeclared(generatedLocalPortName))
            new Port(generatedLocalPortName, outputPortType);
        else
            throw new RuntimeException("Ambiguous local output port name.");

        final String initializationDriverName = node.getInitializationDriver().getText();
        final String initName = generateInitName(initializationDriverName);

        String generatedDriverName = generateName(outputPortName, initName);

        if (!Driver.isDeclared(generatedDriverName)) {
            final Driver driver = new Driver(generatedDriverName, initializationDriverName, 0);

            driver.addParameter(Port.getPort(generatedLocalPortName));
        } else
            throw new RuntimeException("Ambiguous output port initialization driver name.");

        final String copyDriverName = generateCopyName(outputPortType);

        generatedDriverName = generateName(outputPortName, copyDriverName);

        if (!Driver.isDeclared(generatedDriverName)) {
            // FIXME: copyDriverName not valid if simulinkGiotto is set
            final Driver driver = new Driver(generatedDriverName, copyDriverName, outputPortName, 0);

            driver.addParameter(Port.getPort(generatedLocalPortName));
            driver.addParameter(Port.getPort(generatedGlobalPortName));
        } else
            throw new RuntimeException("Ambiguous output port copy driver name.");
    }

    private class GenTaskInputStatePortAllocation extends DepthFirstAdapter {
        private Task task;
        final private ArrayList inputParameters = new ArrayList();

        public GenTaskInputStatePortAllocation(Task task) {
            this.task = task;
        }

        private void generateInputStatePort(String generatedPortName, String inputStatePortType) {
            if (!Port.isDeclared(generatedPortName))
                new Port(generatedPortName, inputStatePortType);
            else
                throw new RuntimeException("Ambiguous input or state port name.");
        }

        public void outAFormalPort(AFormalPort node) {
            final String inputPortName = node.getPortName().getText();
            final String inputPortType = node.getTypeName().getText();

            final String generatedPortName = generateInputStatePortName(task.getName(), inputPortName);

            inputParameters.add(generatedPortName);

            generateInputStatePort(generatedPortName, inputPortType);
        }

        public void outAStatePort(AStatePort node) {
            final String statePortName = node.getPortName().getText();
            final String statePortType = node.getTypeName().getText();

            final String generatedPortName = generateInputStatePortName(task.getName(), statePortName);

            generateInputStatePort(generatedPortName, statePortType);

            final String initializationDriverName = node.getInitializationDriver().getText();
            final String initName = generateInitName(initializationDriverName);

            final String generatedDriverName = generateName(generatedPortName, initName);

            if (!Driver.isDeclared(generatedDriverName)) {
                final int taskIndex = task.getIndex();

                final Driver driver = new Driver(generatedDriverName, initializationDriverName, 1 << taskIndex);

                driver.addParameter(Port.getPort(generatedPortName));
            } else
                throw new RuntimeException("Ambiguous state port initialization driver name.");
        }

        public void outATaskDeclaration(ATaskDeclaration node) {
            taskInputParameters.put(task.getName(), inputParameters);
        }
    }

    public void inATaskDeclaration(ATaskDeclaration node) {
        final String taskName = node.getTaskName().getText();

        if (!Task.isDeclared(taskName)) {
            final int nTasks = Task.getNumberOf();

            // 32 is sizeof(unsigned), which is the type of the active tasks bitfield.
            if (nTasks > 31)
                throw new RuntimeException("Too many tasks: " + nTasks + ".");

            final Task task = new Task(taskName);

            currentTask = task;

            // Must be called after putting task into the table because we need to get the task's index
            node.apply(new GenTaskInputStatePortAllocation(task));
        } else
            throw new RuntimeException("Ambiguous task names.");
    }

    private class GenTaskImplementationParameter extends DepthFirstAdapter {
        private Task task;

        public GenTaskImplementationParameter(Task task) {
            this.task = task;
        }

        public void outAActualScopedParameter(AActualScopedParameter node) {
            final String parameterName = node.getParameterName().getText();

            Port port;

            if (symbolTable.outputPorts.containsKey(parameterName)) {
                // Compute protection mask

                final AOutputDeclaration outputDeclaration =
                    (AOutputDeclaration) symbolTable.outputPorts.get(parameterName);

                final String initializationDriverName = outputDeclaration.getInitializationDriver().getText();

                // already computed protection mask
                Driver driver = getInitOutputDriver(parameterName, initializationDriverName);

                int protection = driver.getProtection();

                final int taskIndex = task.getIndex();

                driver.setProtection(protection | (1 << taskIndex));

                final String parameterType = outputDeclaration.getTypeName().getText();

                // already computed protection mask
                driver = getCopyDriver(parameterName, parameterType);

                protection = driver.getProtection();

                driver.setProtection(protection | (1 << taskIndex));

                port = getLocalPort(parameterName);
            } else
                port = getInputStatePort(task.getName(), parameterName);

            task.addParameter(port);
        }
    }

    public void inAScheduleTask(AScheduleTask node) {
        final String taskImplementation = node.getTaskImplementation().getText();

        currentTask.setCodeName(taskImplementation);

        node.apply(new GenTaskImplementationParameter(currentTask));
    }

    public void outATaskDeclaration(ATaskDeclaration node) {
        currentTask = null;
    }

    private class ComputeDriverDestinationParameters extends DepthFirstAdapter {
        private String driverName;
        final private ArrayList destinationParameters = new ArrayList();

        public ComputeDriverDestinationParameters(String driverName) {
            this.driverName = driverName;
        }

        public void outAFormalPort(AFormalPort node) {
            final String destinationPortName = node.getPortName().getText();

            destinationParameters.add(destinationPortName);
        }

        public void outADriverDeclaration(ADriverDeclaration node) {
            driverDestinationParameters.put(driverName, destinationParameters);
        }
    }

    public void inADriverDeclaration(ADriverDeclaration node) {
        final String driverName = node.getDriverName().getText();

        node.apply(new ComputeDriverDestinationParameters(driverName));
    }

    private class GenConditionWrapper extends DepthFirstAdapter {
        private Condition condition;

        public GenConditionWrapper(Condition condition) {
            this.condition = condition;
        }

        private class GenConditionImplementationParameter extends DepthFirstAdapter {
            public void outAActualScopedParameter(AActualScopedParameter node) {
                final String parameterName = node.getParameterName().getText();

                if (symbolTable.outputPorts.containsKey(parameterName)) {
                    final Port port = getGlobalPort(parameterName);

                    condition.addParameter(port);
                } else {
                    final Port port = Port.getPort(parameterName);

                    condition.addParameter(port);
                }
            }
        }

        public void inACallDriver(ACallDriver node) {
            final String guardImplementation = node.getGuardImplementation().getText();

            condition.setCodeName(guardImplementation);

            final PGuardParameters guardParameters = node.getGuardParameters();

            guardParameters.apply(new GenConditionImplementationParameter());
        }
    }

    private class GenDriverWrapper extends DepthFirstAdapter {
        private Driver driver;
        private Hashtable destinationScope;

        public GenDriverWrapper(Driver driver, Hashtable destinationScope) {
            this.driver = driver;
            this.destinationScope = destinationScope;
        }

        private class GenDriverImplementationParameter extends DepthFirstAdapter {
            public void outAActualScopedParameter(AActualScopedParameter node) {
                String parameterName = node.getParameterName().getText();

                if (destinationScope.containsKey(parameterName))
                    parameterName = (String) destinationScope.get(parameterName);

                if (symbolTable.outputPorts.containsKey(parameterName)) {
                    final Port port = getGlobalPort(parameterName);

                    driver.addParameter(port);
                } else {
                    final Port port = Port.getPort(parameterName);

                    driver.addParameter(port);
                }
            }
        }

        public void inACallDriver(ACallDriver node) {
            final String driverImplementation = node.getDriverImplementation().getText();

            driver.setCodeName(driverImplementation);

            final PActualScopedParameters driverParameters = node.getDriverParameters();

            driverParameters.apply(new GenDriverImplementationParameter());
        }
    }

    private void generateDriver(
            String driverName,
            String destinationName,
            ArrayList actualParameters,
            int protection) {
        final String generatedDriverName = generateName(destinationName, driverName);

        // Generate generatedDriverName only once
        if (!Driver.isDeclared(generatedDriverName)) {

            final Driver driver = new Driver(generatedDriverName, protection);

            final ADriverDeclaration driverDeclaration = (ADriverDeclaration) symbolTable.drivers.get(driverName);

            // if statement is redundant
            if (!Condition.isDeclared(generatedDriverName)) {
                final Condition condition = new Condition(generatedDriverName, 0);

                driverDeclaration.apply(new GenConditionWrapper(condition));
            }

            final ArrayList destinationParameters = (ArrayList) driverDestinationParameters.get(driverName);

            final Hashtable destinationScope = new Hashtable();

            final ListIterator actualIterator = actualParameters.listIterator();

            for (ListIterator destinationIterator = destinationParameters.listIterator();
                 destinationIterator.hasNext();
                 ) {
                String destinationPortName = (String) destinationIterator.next();
                String actualPortName = (String) actualIterator.next();

                destinationScope.put(destinationPortName, actualPortName);
            }

            driverDeclaration.apply(new GenDriverWrapper(driver, destinationScope));
        }
    }

    private class ComputeModeOutputParameters extends DepthFirstAdapter {
        private ArrayList outputParameters = null;

        public void inAModeDeclaration(AModeDeclaration node) {
            outputParameters = new ArrayList();
        }

        public void outAActualPort(AActualPort node) {
            final String outputPortName = node.getPortName().getText();

            outputParameters.add(outputPortName);
        }

        public void outAModeDeclaration(AModeDeclaration node) {
            final String modeName = node.getModeName().getText();

            modeOutputParameters.put(modeName, outputParameters);
        }
    }

    public void inAModeDeclarationSequence(AModeDeclarationSequence node) {
        node.apply(new ComputeModeOutputParameters());
    }

    public void inAModeDeclaration(AModeDeclaration node) {
        currentModeName = node.getModeName().getText();
    }

    public void outAActuatorUpdate(AActuatorUpdate node) {
        final String driverName = node.getDriverName().getText();
        final String actuatorPortName = node.getActuatorPortName().getText();

        final ArrayList destinationParameters = new ArrayList();

        destinationParameters.add(actuatorPortName);

        generateDriver(driverName, actuatorPortName, destinationParameters, 0);
    }

    private class GenMessageWrapper extends DepthFirstAdapter {
        private Task message;

        public GenMessageWrapper(Task message) {
            this.message = message;
        }

        public void outAActualGlobalParameter(AActualGlobalParameter node) {
            message.setCodeName("send_to_all_hosts");

            final String parameterName = node.getParameterName().getText();

            if (symbolTable.sensorPorts.containsKey(parameterName))
                message.addParameter(getSensorPort(parameterName));
            else if (symbolTable.outputPorts.containsKey(parameterName))
                message.addParameter(getLocalPort(parameterName));
            else
                throw new RuntimeException("Invalid port type in schedule connection annotation.");
        }

        public void outAScheduleConnectionList(AScheduleConnectionList node) {
            // FIXME: send only to specific hosts
        }
    }

    public void outAModeConnectionAnnotation(AModeConnectionAnnotation node) {
        // Mode connection annotation

        if (!pureGiotto && node.getScheduleConnectionList() != null) {
            final String generatedMessageName = currentModeName;

            if (!Task.isDeclared(generatedMessageName)) {
                final int nTasks = Task.getNumberOf();

                // 32 is sizeof(unsigned), which is the type of the active tasks bitfield.
                if (nTasks > 31)
                    throw new RuntimeException("Too many tasks: " + nTasks + ".");

                final Task message = new Task(generatedMessageName, currentModeName);

                node.apply(new GenMessageWrapper(message));
            } else
                throw new RuntimeException("Ambiguous task message names.");
        }
    }

    public void outAModeSwitch(AModeSwitch node) {
        final String driverName = node.getDriverName().getText();
        final String modeName = node.getModeName().getText();

        if (dynamicGiotto && !symbolTable.modes.containsKey(modeName))
            // Mode drivers for mode switches to undeclared modes in dynamic Giotto
            // must not have any destination ports.
            // Thus the driver name already constitutes a unique name for the driver.
            generateDriver(driverName, "", new ArrayList(), 0);
        else
            generateDriver(driverName, modeName, (ArrayList) modeOutputParameters.get(modeName), 0);
    }

    public void outATaskInvocation(ATaskInvocation node) {
        final String taskName = node.getTaskName().getText();

        if (node.getDriverName() != null) {
            final String driverName = node.getDriverName().getText();

            final int taskIndex = Task.getTask(taskName).getIndex();

            generateDriver(driverName, taskName, (ArrayList) taskInputParameters.get(taskName), 1 << taskIndex);
        }

        // Task annotation

        if (!pureGiotto && node.getTaskAnnotation() != null) {
            ATaskAnnotation taskAnnotation = (ATaskAnnotation) node.getTaskAnnotation();

            if (taskAnnotation.getScheduleConnectionList() != null) {
                final String generatedMessageName = generateName(currentModeName, taskName);

                if (!Task.isDeclared(generatedMessageName)) {
                    final int nTasks = Task.getNumberOf();

                    // 32 is sizeof(unsigned), which is the type of the active tasks bitfield.
                    if (nTasks > 31)
                        throw new RuntimeException("Too many tasks: " + nTasks + ".");

                    final Task message = new Task(generatedMessageName, currentModeName);

                    taskAnnotation.apply(new GenMessageWrapper(message));
                } else
                    throw new RuntimeException("Ambiguous task message names.");
            }
        }
    }

    public void outAModeDeclaration(AModeDeclaration node) {
        currentModeName = null;
    }

}
