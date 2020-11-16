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

import giotto.giottoc.analysis.DepthFirstAdapter;
import giotto.giottoc.node.AActualGlobalParameter;
import giotto.giottoc.node.AActualPort;
import giotto.giottoc.node.AActualPortList;
import giotto.giottoc.node.AActualPorts;
import giotto.giottoc.node.AActualScopedParameter;
import giotto.giottoc.node.AActuatorDeclaration;
import giotto.giottoc.node.AActuatorUpdate;
import giotto.giottoc.node.ADriverDeclaration;
import giotto.giottoc.node.AFormalPort;
import giotto.giottoc.node.AFormalPortList;
import giotto.giottoc.node.AFormalPorts;
import giotto.giottoc.node.AGuardParameters;
import giotto.giottoc.node.AHostNameIdent;
import giotto.giottoc.node.AModeConnectionAnnotation;
import giotto.giottoc.node.AModeDeclaration;
import giotto.giottoc.node.AModeDeclarationSequence;
import giotto.giottoc.node.AModeSwitch;
import giotto.giottoc.node.ANetworkNameIdent;
import giotto.giottoc.node.AOutputDeclaration;
import giotto.giottoc.node.ATaskAnnotation;
import giotto.giottoc.node.ATaskDeclaration;
import giotto.giottoc.node.ATaskInvocation;
import giotto.giottoc.node.TIdent;
import giotto.giottoc.node.Token;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

/**

This class implements type checking.

@author Christoph Kirsch
@version TypeChecker.java,v 1.8 2004/09/29 03:52:36 cxh Exp
@since Giotto 1.0.1
*/
public class TypeChecker extends DepthFirstAdapter {
    private SymbolTable symbolTable;

    private boolean dynamicGiotto;

    private boolean inATaskDeclaration = false;

    private boolean inADriverDeclaration = false;

    private boolean inAModeDeclaration = false;

    private boolean inAGuardParameters = false;

    private boolean inAModeConnectionAnnotation = false;

    private boolean inATaskAnnotation = false;

    private ScopedSymbolTable scopedSymbolTable;

    private HashSet taskInvocations = null;
    private HashSet portUpdates = null;
    private HashSet modeSwitches = null;

    public TypeChecker(SymbolTable symbolTable, boolean dynamicGiotto) {
        this.symbolTable = symbolTable;

        this.dynamicGiotto = dynamicGiotto;
    }

    // Action code

    public void inATaskDeclaration(ATaskDeclaration node) {
        inATaskDeclaration = true;

        node.apply(scopedSymbolTable = new ScopedSymbolTable(symbolTable));
    }

    public void outATaskDeclaration(ATaskDeclaration node) {
        inATaskDeclaration = false;

        scopedSymbolTable = null;
    }

    public void inADriverDeclaration(ADriverDeclaration node) {
        inADriverDeclaration = true;

        node.apply(scopedSymbolTable = new ScopedSymbolTable(symbolTable));
    }

    public void outADriverDeclaration(ADriverDeclaration node) {
        inADriverDeclaration = false;

        scopedSymbolTable = null;
    }

    public void inAModeDeclaration(AModeDeclaration node) {
        inAModeDeclaration = true;

        taskInvocations = new HashSet();
        portUpdates = new HashSet();
        modeSwitches = new HashSet();
    }

    public void outAModeDeclaration(AModeDeclaration node) {
        inAModeDeclaration = false;

        taskInvocations = null;
        portUpdates = null;
        modeSwitches = null;
    }

    public void outAActualPort(AActualPort node) {
        final String name = node.getPortName().getText();

        if (inATaskDeclaration) {
            if (!symbolTable.outputPorts.containsKey(name)) {
                errorMessage(node.getPortName(), name, "output port");
            }
        } else if (inADriverDeclaration) {
            if (!symbolTable.sensorPorts.containsKey(name) && !symbolTable.outputPorts.containsKey(name)) {
                errorMessage(node.getPortName(), name, "sensor or output port");
            }
        } else if (inAModeDeclaration) {
            if (!symbolTable.outputPorts.containsKey(name)) {
                errorMessage(node.getPortName(), name, "output port");
            }
        }
    }

    public void inAGuardParameters(AGuardParameters node) {
        inAGuardParameters = true;
    }

    public void outAActualScopedParameter(AActualScopedParameter node) {
        final String name = node.getParameterName().getText();

        if (inAGuardParameters) {
            if (!scopedSymbolTable.actualPorts.contains(name)) {
                errorMessage(node.getParameterName(), name, "source port");
            }
        } else {
            if (!scopedSymbolTable.declarations.containsKey(name)) {
                if (inATaskDeclaration)
                    errorMessage(node.getParameterName(), name, "local input, output, or state port");
                else if (inADriverDeclaration)
                    errorMessage(node.getParameterName(), name, "source or destination port");
                else
                    throw new RuntimeException("Error while checking parameters!");
            }
        }
    }

    public void outAGuardParameters(AGuardParameters node) {
        inAGuardParameters = false;
    }

    public void outAModeDeclarationSequence(AModeDeclarationSequence node) {
        final String name = node.getModeName().getText();

        if (!symbolTable.modes.containsKey(name)) {
            errorMessage(node.getModeName(), name, "mode");
        }
    }

    public void outAActuatorUpdate(AActuatorUpdate node) {
        final TIdent actuatorPort = node.getActuatorPortName();
        final String actuatorPortName = actuatorPort.getText();

        if (!symbolTable.actuatorPorts.containsKey(actuatorPortName)) {
            errorMessage(actuatorPort, actuatorPortName, "actuator port");
        }

        final String driverName = node.getDriverName().getText();

        if (!symbolTable.drivers.containsKey(driverName)) {
            errorMessage(node.getDriverName(), driverName, "driver");
        }

        if (!portUpdates.add(actuatorPortName)) {
            errorPortUpdate(actuatorPort, actuatorPortName);
        }

        AActuatorDeclaration actuatorPortDeclaration =
            (AActuatorDeclaration) symbolTable.actuatorPorts.get(actuatorPortName);

        ADriverDeclaration driverDeclaration = (ADriverDeclaration) symbolTable.drivers.get(driverName);
        AFormalPorts destinationPorts = (AFormalPorts) driverDeclaration.getDestinationPorts();
        AFormalPortList destinationPortList = (AFormalPortList) destinationPorts.getFormalPortList();

        if (destinationPortList != null) {
            LinkedList destinationFormalPort = destinationPortList.getFormalPort();

            if (destinationFormalPort.size() == 1) {
                ListIterator destinationIterator = destinationFormalPort.listIterator();

                // destinationIterator has at least one element
                AFormalPort destinationPort = (AFormalPort) destinationIterator.next();

                TIdent destinationPortType = destinationPort.getTypeName();
                TIdent actuatorPortType = actuatorPortDeclaration.getTypeName();

                if (destinationPortType.getText().compareTo(actuatorPortType.getText()) != 0)
                    errorActuatorDriverPortType(actuatorPort, driverName);
            } else
                errorActuatorDriverLength(actuatorPort, driverName);
        } else
            errorActuatorDriverLength(actuatorPort, driverName);
    }

    public void outAModeSwitch(AModeSwitch node) {
        final TIdent mode = node.getModeName();
        final String modeName = mode.getText();

        if (symbolTable.modes.containsKey(modeName)) {
            final String driverName = node.getDriverName().getText();

            if (!modeSwitches.add(modeName + driverName))
                errorModeSwitch(mode, driverName);

            if (!symbolTable.drivers.containsKey(driverName)) {
                errorMessage(node.getDriverName(), driverName, "driver");
            }

            AModeDeclaration modeDeclaration = (AModeDeclaration) symbolTable.modes.get(modeName);

            ADriverDeclaration driverDeclaration = (ADriverDeclaration) symbolTable.drivers.get(driverName);
            AFormalPorts destinationPorts = (AFormalPorts) driverDeclaration.getDestinationPorts();
            AFormalPortList destinationPortList = (AFormalPortList) destinationPorts.getFormalPortList();

            AActualPorts modePorts = (AActualPorts) modeDeclaration.getModePorts();
            AActualPortList modePortList = (AActualPortList) modePorts.getActualPortList();

            if (destinationPortList != null) {
                LinkedList destinationFormalPort = destinationPortList.getFormalPort();
                ListIterator destinationPortIterator = destinationFormalPort.listIterator();

                if (modePortList != null) {
                    LinkedList modeActualPort = modePortList.getActualPort();
                    ListIterator modePortIterator = modeActualPort.listIterator();

                    for (; destinationPortIterator.hasNext();) {
                        AFormalPort destinationPort = (AFormalPort) destinationPortIterator.next();

                        if (modePortIterator.hasNext()) {
                            AActualPort modePort = (AActualPort) modePortIterator.next();

                            TIdent destinationPortType = destinationPort.getTypeName();

                            String modePortName = modePort.getPortName().getText();
                            AOutputDeclaration modePortDeclaration =
                                (AOutputDeclaration) symbolTable.outputPorts.get(modePortName);
                            TIdent modePortType = modePortDeclaration.getTypeName();

                            if (destinationPortType.getText().compareTo(modePortType.getText()) != 0)
                                errorModeDriverPortType(
                                        mode,
                                        driverName,
                                        destinationPort.getPortName(),
                                        modePort.getPortName());
                        } else
                            errorModeDriverLength(mode, driverName, destinationPort.getPortName());
                    }

                    if (modePortIterator.hasNext()) {
                        AActualPort modePort = (AActualPort) modePortIterator.next();

                        errorModeDriverLength(mode, driverName, modePort.getPortName());
                    }
                } else {
                    // destinationPortIterator has at least one element
                    AFormalPort destinationPort = (AFormalPort) destinationPortIterator.next();

                    errorModeDriverLength(mode, driverName, destinationPort.getPortName());
                }
            } else if (modePortList != null) {
                LinkedList modeActualPort = modePortList.getActualPort();
                ListIterator modePortIterator = modeActualPort.listIterator();

                // modePortIterator has at least one element
                AActualPort modePort = (AActualPort) modePortIterator.next();

                errorModeDriverLength(mode, driverName, modePort.getPortName());
            }
        } else if (dynamicGiotto) {
            final String driverName = node.getDriverName().getText();

            if (!modeSwitches.add(modeName + driverName))
                errorModeSwitch(mode, driverName);

            if (!symbolTable.drivers.containsKey(driverName)) {
                errorMessage(node.getDriverName(), driverName, "driver");
            }

            ADriverDeclaration driverDeclaration = (ADriverDeclaration) symbolTable.drivers.get(driverName);
            AFormalPorts destinationPorts = (AFormalPorts) driverDeclaration.getDestinationPorts();
            AFormalPortList destinationPortList = (AFormalPortList) destinationPorts.getFormalPortList();

            if (destinationPortList != null)
                errorUndeclaredModeDriverLength(mode, driverName);
        } else
            errorMessage(mode, modeName, "mode");
    }

    private class CheckOutputPortUpdates extends DepthFirstAdapter {
        private TIdent task;

        public CheckOutputPortUpdates(TIdent task) {
            this.task = task;
        }

        public void outAActualPort(AActualPort node) {
            final TIdent outputPort = node.getPortName();
            final String outputPortName = outputPort.getText();

            if (!portUpdates.add(outputPortName)) {
                errorPortUpdate(task, outputPortName);
            }
        }
    }

    public void outATaskInvocation(ATaskInvocation node) {
        final TIdent task = node.getTaskName();
        final String taskName = task.getText();

        if (!symbolTable.tasks.containsKey(taskName)) {
            errorMessage(task, taskName, "task");
        } else {
            if (!taskInvocations.add(taskName))
                errorTaskInvocation(task);

            ATaskDeclaration taskDeclaration = (ATaskDeclaration) symbolTable.tasks.get(taskName);

            taskDeclaration.apply(new CheckOutputPortUpdates(task));
        }

        ATaskDeclaration taskDeclaration = (ATaskDeclaration) symbolTable.tasks.get(taskName);

        AFormalPorts inputPorts = (AFormalPorts) taskDeclaration.getInputPorts();
        AFormalPortList inputPortList = (AFormalPortList) inputPorts.getFormalPortList();

        if (node.getDriverName() == null) {
            if (inputPortList != null)
                errorTaskNoDriverLength(task);
        } else {
            final String driverName = node.getDriverName().getText();

            if (!symbolTable.drivers.containsKey(driverName)) {
                errorMessage(node.getDriverName(), driverName, "driver");
            }

            ADriverDeclaration driverDeclaration = (ADriverDeclaration) symbolTable.drivers.get(driverName);
            AFormalPorts destinationPorts = (AFormalPorts) driverDeclaration.getDestinationPorts();
            AFormalPortList destinationPortList = (AFormalPortList) destinationPorts.getFormalPortList();

            if (destinationPortList != null) {
                LinkedList destinationFormalPort = destinationPortList.getFormalPort();
                ListIterator destinationPortIterator = destinationFormalPort.listIterator();

                if (inputPortList != null) {
                    LinkedList inputFormalPort = inputPortList.getFormalPort();
                    ListIterator inputPortIterator = inputFormalPort.listIterator();

                    for (; destinationPortIterator.hasNext();) {
                        AFormalPort destinationPort = (AFormalPort) destinationPortIterator.next();

                        if (inputPortIterator.hasNext()) {
                            AFormalPort inputPort = (AFormalPort) inputPortIterator.next();

                            TIdent destinationPortType = destinationPort.getTypeName();
                            TIdent inputPortType = inputPort.getTypeName();

                            if (destinationPortType.getText().compareTo(inputPortType.getText()) != 0)
                                errorTaskDriverPortType(
                                        task,
                                        driverName,
                                        destinationPort.getPortName(),
                                        inputPort.getPortName());
                        } else
                            errorTaskDriverLength(task, driverName, destinationPort.getPortName());
                    }

                    if (inputPortIterator.hasNext()) {
                        AFormalPort inputPort = (AFormalPort) inputPortIterator.next();

                        errorTaskDriverLength(task, driverName, inputPort.getPortName());
                    }
                } else {
                    // destinationPortIterator has at least one element
                    AFormalPort destinationPort = (AFormalPort) destinationPortIterator.next();

                    errorTaskDriverLength(task, driverName, destinationPort.getPortName());
                }
            } else if (inputPortList != null) {
                LinkedList inputFormalPort = inputPortList.getFormalPort();
                ListIterator inputPortIterator = inputFormalPort.listIterator();

                // inputPortIterator has at least one element
                AFormalPort inputPort = (AFormalPort) inputPortIterator.next();

                errorTaskDriverLength(task, driverName, inputPort.getPortName());
            }
        }
    }

    public void outAHostNameIdent(AHostNameIdent node) {
        final String hostName = node.getIdent().getText();

        if (!symbolTable.hosts.containsKey(hostName)) {
            errorMessage(node.getIdent(), hostName, "host");
        }
    }

    public void outANetworkNameIdent(ANetworkNameIdent node) {
        final String networkName = node.getIdent().getText();

        if (!symbolTable.networks.containsKey(networkName)) {
            errorMessage(node.getIdent(), networkName, "network");
        }
    }

    public void inAModeConnectionAnnotation(AModeConnectionAnnotation node) {
        inAModeConnectionAnnotation = true;
    }

    public void inATaskAnnotation(ATaskAnnotation node) {
        inATaskAnnotation = true;
    }

    public void outAActualGlobalParameter(AActualGlobalParameter node) {
        final String name = node.getParameterName().getText();

        if (inAModeConnectionAnnotation && !symbolTable.sensorPorts.containsKey(name)) {
            errorMessage(node.getParameterName(), name, "sensor port");
        } else if (inATaskAnnotation && !symbolTable.outputPorts.containsKey(name)) {
            errorMessage(node.getParameterName(), name, "output port");
        }

    }

    public void outATaskAnnotation(ATaskAnnotation node) {
        inATaskAnnotation = false;
    }

    public void outAModeConnectionAnnotation(AModeConnectionAnnotation node) {
        inAModeConnectionAnnotation = false;
    }

    // Misc code

    private static void errorMessage(Token token, String name, String message) {
        throw new RuntimeException(
                "[" + token.getLine() + "," + token.getPos() + "] " + name + " is not declared as " + message + ".");
    }

    private static void errorTaskInvocation(Token token) {
        throw new RuntimeException(
                "["
                + token.getLine()
                + ","
                + token.getPos()
                + "] "
                + "Task "
                + token.getText()
                + " is invoked multiple times in the same mode.");
    }

    private static void errorPortUpdate(Token token, String portName) {
        throw new RuntimeException(
                "[" + token.getLine() + "," + token.getPos() + "] " + "Port " + portName + " has multiple owners.");
    }

    private static void errorModeSwitch(Token token, String driverName) {
        throw new RuntimeException(
                "["
                + token.getLine()
                + ","
                + token.getPos()
                + "] "
                + "Mode switch to mode: "
                + token.getText()
                + " with driver: "
                + driverName
                + " is multiple times in the same mode.");
    }

    private static void errorActuatorDriverPortType(Token actuatorPortToken, String driverName) {
        throw new RuntimeException(
                "["
                + actuatorPortToken.getLine()
                + ","
                + actuatorPortToken.getPos()
                + "] Type mismatch at actuator update of "
                + actuatorPortToken.getText()
                + " by driver "
                + driverName
                + ".");
    }

    private static void errorActuatorDriverLength(Token actuatorPortToken, String driverName) {
        throw new RuntimeException(
                "["
                + actuatorPortToken.getLine()
                + ","
                + actuatorPortToken.getPos()
                + "] Driver "
                + driverName
                + " at actuator update of "
                + actuatorPortToken.getText()
                + " must have exactly one destination port.");
    }

    private static void errorUndeclaredModeDriverLength(Token modeToken, String driverName) {
        throw new RuntimeException(
                "["
                + modeToken.getLine()
                + ","
                + modeToken.getPos()
                + "]"
                + " A mode switch to an undeclared mode "
                + modeToken.getText()
                + " driver "
                + driverName
                + " must not have any destination ports.");
    }

    private static void errorModeDriverLength(Token modeToken, String driverName, Token portToken) {
        throw new RuntimeException(
                "["
                + modeToken.getLine()
                + ","
                + modeToken.getPos()
                + "]"
                + " Number of ports mismatch at mode switch to "
                + modeToken.getText()
                + " with driver "
                + driverName
                + ";"
                + " Port "
                + portToken.getText()
                + " ["
                + portToken.getLine()
                + ","
                + portToken.getPos()
                + "].");
    }

    private static void errorModeDriverPortType(
            Token modeToken,
            String driverName,
            Token destinationPortToken,
            Token modePortToken) {
        throw new RuntimeException(
                "["
                + modeToken.getLine()
                + ","
                + modeToken.getPos()
                + "]"
                + " Type mismatch at mode switch to "
                + modeToken.getText()
                + " with driver "
                + driverName
                + ";"
                + " Destination port "
                + destinationPortToken.getText()
                + " ["
                + destinationPortToken.getLine()
                + ","
                + destinationPortToken.getPos()
                + "]"
                + ", mode port "
                + modePortToken.getText()
                + " ["
                + modePortToken.getLine()
                + ","
                + modePortToken.getPos()
                + "].");
    }

    private static void errorTaskNoDriverLength(Token taskToken) {
        throw new RuntimeException(
                "["
                + taskToken.getLine()
                + ","
                + taskToken.getPos()
                + "]"
                + " Number of ports mismatch at task invocation of "
                + taskToken.getText());
    }

    private static void errorTaskDriverLength(Token taskToken, String driverName, Token portToken) {
        throw new RuntimeException(
                "["
                + taskToken.getLine()
                + ","
                + taskToken.getPos()
                + "]"
                + " Number of ports mismatch at task invocation of "
                + taskToken.getText()
                + " with driver "
                + driverName
                + ";"
                + " Port "
                + portToken.getText()
                + " ["
                + portToken.getLine()
                + ","
                + portToken.getPos()
                + "].");
    }

    private static void errorTaskDriverPortType(
            Token taskToken,
            String driverName,
            Token destinationPortToken,
            Token inputPortToken) {
        throw new RuntimeException(
                "["
                + taskToken.getLine()
                + ","
                + taskToken.getPos()
                + "]"
                + " Type mismatch at task invocation of "
                + taskToken.getText()
                + " with driver "
                + driverName
                + ";"
                + " Destination port "
                + destinationPortToken.getText()
                + " ["
                + destinationPortToken.getLine()
                + ","
                + destinationPortToken.getPos()
                + "]"
                + ", input port "
                + inputPortToken.getText()
                + " ["
                + inputPortToken.getLine()
                + ","
                + inputPortToken.getPos()
                + "].");
    }

    public String toString() {
        return symbolTable.toString();
    }
}
