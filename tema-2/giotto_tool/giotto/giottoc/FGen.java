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
import giotto.functionality.table.Parameter;
import giotto.functionality.table.Port;
import giotto.functionality.table.Task;
import giotto.functionality.table.Trigger;

/**
 * @author Christoph Kirsch
 * @version FGen.java,v 1.11 2004/09/29 03:52:00 cxh Exp
 * @since Giotto 1.0.1
 */
public class FGen extends CodeGen {
    private boolean simulinkGiotto;

    public FGen(SymbolTable symbolTable, boolean simulinkGiotto) {
        super(symbolTable, "ftable", "f_table.c", "f_table.h", "f_spec.txt");

        this.simulinkGiotto = simulinkGiotto;
    }

    // Action code

    public void emitCFileHeader() {
        emit("Header");

        emit("Include");
    }

    public void emitCFileBody() {

        int nTasks = 0;
        int nDrivers = 0;
        int nConditions = 0;
        int nPorts = 0;

        // FIXME: emit trigger table according to Trigger class objects

        emit("TriggerTable");

        if (simulinkGiotto)
            emit("EmptyPortTable");
        else {
            //                        for (ListIterator iterator = Port.getListIterator(); iterator.hasNext(); nPorts++) {
            //                                final Port port = (Port) iterator.next();
            //
            //                                emit("PortAllocation", new String[] { port.getType(), port.getName()});
            //                        }

            for (nPorts = 0; nPorts < Port.getNumberOf(); nPorts++) {
                final Port port = Port.getPort(nPorts);

                emit("PortAllocation", new String[] { port.getType(), port.getName()});
            }

            if (nPorts != 0)
                emit("CR");

            nPorts = 0;

            emit("PortTableHeader");

            //                        for (ListIterator iterator = Port.getListIterator(); iterator.hasNext(); nPorts++) {
            //                                final Port port = (Port) iterator.next();
            //
            //                                if (nPorts != 0)
            //                                        emit("TableComma");
            //
            //                                emit("PortTableElement", new String[] { port.getName(), port.getName(), port.getType()});
            //                        }

            for (nPorts = 0; nPorts < Port.getNumberOf(); nPorts++) {
                final Port port = Port.getPort(nPorts);

                if (nPorts != 0)
                    emit("TableComma");

                emit("PortTableElement", new String[] { port.getName(), port.getName(), port.getType()});
            }

            emit("TableEnd");
        }

        emit("CR");

        for (int iterator = 0; iterator < Task.getNumberOf(); iterator++) {
            final Task task = Task.getTask(iterator);

            if (task.isMessage()) {
                emit("MessageWrapperHeader", new String[] { task.getWrapperName()});

                Parameter messageParameter = task.getParameter();

                for (int parameterIterator = 0;
                     parameterIterator < messageParameter.getNops();
                     parameterIterator++) {
                    final Port port = messageParameter.getPort(parameterIterator);

                    emit("MessageImplementationBegin", new String[] { task.getCCodeName() });

                    emit("Parameter", new String[] { Integer.toString(port.getIndex()) });

                    emit("MessageImplementationEnd");
                }

                emit("MessageWrapperEnd");
            } else {
                emit("TaskWrapperHeader", new String[] { task.getWrapperName()});

                emit("ImplementationBegin", new String[] { task.getCCodeName()});

                if (!simulinkGiotto) {
                    Parameter taskParameter = task.getParameter();

                    int nParameters = 0;

                    for (nParameters = 0;
                         nParameters < taskParameter.getNops();
                         nParameters++) {
                        final Port port = taskParameter.getPort(nParameters);

                        if (nParameters != 0)
                            emit("ParameterComma");

                        emit("PortParameter", new String[] { port.getName()});
                    }
                }

                emit("ImplementationEnd");

                emit("TaskWrapperEnd");
            }
        }

        emit("TaskTableHeader");

        for (nTasks = 0; nTasks < Task.getNumberOf(); nTasks++) {
            final Task task = Task.getTask(nTasks);

            if (nTasks != 0)
                emit("TableComma");

            emit("TaskTableElement", new String[] { task.getName(), task.getWrapperName()});
        }

        emit("TableEnd");

        emit("CR");

        //                for (ListIterator iterator = Driver.getListIterator(); iterator.hasNext();) {
        for (int iterator = 0; iterator < Driver.getNumberOf(); iterator++) {
            //                        final Driver driver = (Driver) iterator.next();
            final Driver driver = Driver.getDriver(iterator);

            emit("DriverWrapperHeader", new String[] { driver.getWrapperName()});

            if (simulinkGiotto && driver.isCopyDriver()) {
                emit("ImplementationBegin", new String[] { driver.getOutputPortName() + "_output_driver_Update" });

                emit("ImplementationEnd");

                emit("ImplementationBegin", new String[] { driver.getOutputPortName() + "_output_driver" });

                emit("ImplementationEnd");
            } else {
                emit("ImplementationBegin", new String[] { driver.getCCodeName()});

                if (!simulinkGiotto) {
                    final Parameter driverParameter = driver.getParameter();

                    int nParameters = 0;

                    for (nParameters = 0;
                         nParameters < driverParameter.getNops();
                         nParameters++) {
                        final Port port = driverParameter.getPort(nParameters);

                        if (nParameters != 0)
                            emit("ParameterComma");

                        emit("PortParameter", new String[] { port.getName()});
                    }
                }

                emit("ImplementationEnd");
            }

            emit("WrapperEnd");
        }

        emit("DriverTableHeader");

        //                for (ListIterator iterator = Driver.getListIterator(); iterator.hasNext(); nDrivers++) {
        for (nDrivers = 0; nDrivers <Driver.getNumberOf(); nDrivers++) {
            final Driver driver = Driver.getDriver(nDrivers);

            if (nDrivers != 0)
                emit("TableComma");

            emit(
                    "DriverConditionTableElement",
                    new String[] { driver.getName(), driver.getWrapperName(), Integer.toString(driver.getProtection())});
        }

        emit("TableEnd");

        emit("CR");

        //                for (ListIterator iterator = Condition.getListIterator(); iterator.hasNext();) {
        for (int iterator = 0; iterator < Condition.getNumberOf(); iterator++) {
            //                        final Condition condition = (Condition) iterator.next();
            final Condition condition = Condition.getCondition(iterator);

            emit("ConditionWrapperHeader", new String[] { condition.getWrapperName()});

            emit("IfGuard", new String[] { condition.getCCodeName()});

            if (!simulinkGiotto) {
                Parameter conditionParameter = condition.getParameter();

                int nParameters = 0;

                for (nParameters = 0;
                     nParameters < conditionParameter.getNops();
                     nParameters++) {
                    final Port port = conditionParameter.getPort(nParameters);

                    if (nParameters != 0)
                        emit("ParameterComma");

                    emit("PortParameter", new String[] { port.getName()});
                }
            }

            emit("ImplementationEnd");

            emit("WrapperEnd");
        }

        emit("ConditionTableHeader");

        for (nConditions = 0; nConditions < Condition.getNumberOf(); nConditions++) {
            Condition condition = Condition.getCondition(nConditions);

            if (nConditions != 0)
                emit("TableComma");

            emit(
                    "DriverConditionTableElement",
                    new String[] {
                        condition.getName(),
                        condition.getWrapperName(),
                        Integer.toString(condition.getProtection())});
        }

        emit("TableEnd");
    }

    public void emitHFileHeader() {
        emit("Header");
    }

    public void emitHFileBody() {
        final int nTasks = Task.getNumberOf();
        final int nDrivers = Driver.getNumberOf();
        final int nConditions = Condition.getNumberOf();
        final int nTriggers = Trigger.getNumberOf();
        final int nPorts = Port.getNumberOf();

        emit(
                "TableSize",
                new String[] {
                    Integer.toString(nDrivers),
                    Integer.toString(nConditions),
                    Integer.toString(nTasks),
                    Integer.toString(nTriggers),
                    Integer.toString(nPorts)});
    }

}
