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
import giotto.giottoc.analysis.DepthFirstAdapter;

/**
 * @author Christoph Kirsch
 * @version Wrapper.java,v 1.9 2004/09/29 03:52:39 cxh Exp
 * @since Giotto 1.0.1
 */
public abstract class Wrapper extends DepthFirstAdapter {
    protected SymbolTable symbolTable;

    /**
     * Constructor for Wrapper.
     */
    public Wrapper(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    // Functionality names

    protected final static String generateName(String prefix, String postfix) {
        return prefix + "_" + postfix;
    }

    protected final static String generateInitName(String driverName) {
        return generateName("init", driverName);
    }

    protected final static String generateCopyName(String portType) {
        return generateName("copy", portType);
    }

    protected final static String generateInputStatePortName(String prefix, String postfix) {
        return generateName(prefix, postfix);
    }

    protected final static String generateGlobalPortName(String portName) {
        return generateName("global", portName);
    }

    protected final static String generateLocalPortName(String portName) {
        return generateName("local", portName);
    }

    // Message access

    private final static Task getMessage(String prefix, String postfix) {
        return Task.getTask(generateName(prefix, postfix));
    }

    protected final static String getMessageWrapperName(String prefix, String postfix) {
        return getMessage(prefix, postfix).getWrapperName();
    }

    protected final static int getMessageIndex(String prefix, String postfix) {
        return getMessage(prefix, postfix).getIndex();
    }

    // Init output driver access

    protected final static Driver getInitOutputDriver(String prefix, String postfix) {
        return (Driver) Driver.getDriver(generateName(prefix, generateInitName(postfix)));
    }

    protected final static String getInitOutputDriverWrapperName(String prefix, String postfix) {
        return getInitOutputDriver(prefix, postfix).getWrapperName();
    }

    protected final static int getInitOutputDriverIndex(String prefix, String postfix) {
        return getInitOutputDriver(prefix, postfix).getIndex();
    }

    // Copy driver access

    protected final static Driver getCopyDriver(String prefix, String postfix) {
        return (Driver) Driver.getDriver(generateName(prefix, generateCopyName(postfix)));
    }

    protected final static String getCopyDriverWrapperName(String prefix, String postfix) {
        return getCopyDriver(prefix, postfix).getWrapperName();
    }

    protected final static int getCopyDriverIndex(String prefix, String postfix) {
        return getCopyDriver(prefix, postfix).getIndex();
    }

    // Init state driver access

    private final static Driver getInitStateDriver(String taskName, String prefix, String postfix) {
        return (Driver) Driver.getDriver(
                generateName(generateInputStatePortName(taskName, prefix), generateInitName(postfix)));
    }

    protected final static String getInitStateDriverWrapperName(String taskName, String prefix, String postfix) {
        return getInitStateDriver(taskName, prefix, postfix).getWrapperName();
    }

    protected final static int getInitStateDriverIndex(String taskName, String prefix, String postfix) {
        return getInitStateDriver(taskName, prefix, postfix).getIndex();
    }

    // Driver access

    protected final static Driver getDriver(String generatedDriverName) {
        return (Driver) Driver.getDriver(generatedDriverName);
    }

    private final static Driver getDriver(String prefix, String postfix) {
        return getDriver(generateName(prefix, postfix));
    }

    protected final static String getDriverWrapperName(String prefix, String postfix) {
        return getDriver(prefix, postfix).getWrapperName();
    }

    protected final static int getDriverIndex(String prefix, String postfix) {
        return getDriver(prefix, postfix).getIndex();
    }

    // Condition access

    private final static Condition getCondition(String prefix, String postfix) {
        return Condition.getCondition(generateName(prefix, postfix));
    }

    protected final static String getConditionWrapperName(String prefix, String postfix) {
        return getCondition(prefix, postfix).getWrapperName();
    }

    protected final static int getConditionIndex(String prefix, String postfix) {
        return getCondition(prefix, postfix).getIndex();
    }

    // Port access

    protected final static Port getInputStatePort(String prefix, String postfix) {
        return Port.getPort(generateInputStatePortName(prefix, postfix));
    }

    protected final static Port getGlobalPort(String portName) {
        return Port.getPort(generateGlobalPortName(portName));
    }

    protected final static Port getLocalPort(String portName) {
        return Port.getPort(generateLocalPortName(portName));
    }

    protected final static Port getSensorPort(String portName) {
        return Port.getPort(portName);
    }

}
