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
import giotto.giottoc.node.AActuatorUpdate;
import giotto.giottoc.node.AModeDeclaration;
import giotto.giottoc.node.AModeSwitch;
import giotto.giottoc.node.AProgram;
import giotto.giottoc.node.ATaskInvocation;
import giotto.giottoc.node.Token;

import java.util.LinkedList;
import java.util.ListIterator;

/**

This class checks whether the input program is 'well-timed'.

Arkadeb Ghosal, arkadeb@eecs.berkeley.edu

@author Christoph Kirsch
@version FrequencyChecker.java,v 1.9 2004/09/29 03:52:04 cxh Exp
@since Giotto 1.0.1
*/
public class FrequencyChecker extends DepthFirstAdapter
{
    private boolean dynamicGiotto;

    private Token modePeriodToken = null;

    private boolean someTaskFrequencyEqualOne = false;

    private SymbolTable symbolTable;

    public FrequencyChecker(SymbolTable symbolTable, boolean dynamicGiotto)
    {
        this.symbolTable = symbolTable;

        this.dynamicGiotto = dynamicGiotto;
    }

    public void outAProgram(AProgram node)
    {
        node.apply(new CheckModeSwitches());
    }

    public void inAModeDeclaration(AModeDeclaration node)
    {
        modePeriodToken = node.getModePeriod();

        final int modePeriod = Integer.parseInt(modePeriodToken.getText());

        if (modePeriod == 0)
            errorZero(modePeriodToken);

        someTaskFrequencyEqualOne = false;
    }

    private void checkFrequency(Token frequencyToken)
    {
        final int modePeriod = Integer.parseInt(modePeriodToken.getText());
        final int frequency = Integer.parseInt(frequencyToken.getText());

        if (frequency == 0)
            errorZero(frequencyToken);

        if ((modePeriod % frequency) != 0)
            errorFrequency(frequencyToken, modePeriodToken.getText());
    }

    public void outAActuatorUpdate(AActuatorUpdate node)
    {
        checkFrequency(node.getActuatorFrequency());
    }


    public void outAModeSwitch(AModeSwitch node)
    {
        checkFrequency(node.getModeSwitchFrequency());
    }


    public void outATaskInvocation(ATaskInvocation node)
    {
        final Token frequency = node.getTaskFrequency();

        if (Integer.parseInt(frequency.getText()) == 1)
            someTaskFrequencyEqualOne = true;

        checkFrequency(frequency);
    }

    public void outAModeDeclaration(AModeDeclaration node)
    {
        modePeriodToken = null;

        // Not required anymore:

        // if (!someTaskFrequencyEqualOne)
        // errorTaskFrequencyEqualOne(node.getModeName());
        // else

        someTaskFrequencyEqualOne = false;
    }

    private class CheckModeSwitches extends DepthFirstAdapter
    {
        public void caseAModeDeclaration(AModeDeclaration node)        {
            final int modePeriod = Integer.parseInt(node.getModePeriod().getText());

            final LinkedList taskInvocationList = node.getTaskInvocationList();

            for (ListIterator taskInvocationIterator = taskInvocationList.listIterator(); taskInvocationIterator.hasNext();) {
                ATaskInvocation taskInvocation = (ATaskInvocation) taskInvocationIterator.next();

                final String taskInvocationName = taskInvocation.getTaskName().getText();

                final int taskInvocationFrequency = Integer.parseInt(taskInvocation.getTaskFrequency().getText());

                final LinkedList modeSwitchList = node.getModeSwitchList();

                for (ListIterator modeSwitchIterator = modeSwitchList.listIterator(); modeSwitchIterator.hasNext();) {
                    AModeSwitch modeSwitch = (AModeSwitch) modeSwitchIterator.next();

                    final int modeSwitchFrequency = Integer.parseInt(modeSwitch.getModeSwitchFrequency().getText());

                    final String nextModeName = modeSwitch.getModeName().getText();

                    if ((taskInvocationFrequency % modeSwitchFrequency) != 0) {
                        if (dynamicGiotto && !symbolTable.modes.containsKey(nextModeName)) {
                            if (modeSwitchFrequency != 1)
                                errorFrequencyUndeclaredMode(modeSwitch.getModeName());
                        } else {
                            // symbolTable.modes.containsKey(nextModeName) is true because we checked this in TypeChecker
                            final AModeDeclaration nextModeDeclaration = (AModeDeclaration) symbolTable.modes.get(nextModeName);

                            final int nextModePeriod = Integer.parseInt(nextModeDeclaration.getModePeriod().getText());

                            final LinkedList nextTaskInvocationList = nextModeDeclaration.getTaskInvocationList();

                            boolean noNextTaskInvocation = true;

                            for (ListIterator nextTaskInvocationIterator = nextTaskInvocationList.listIterator();
                                 noNextTaskInvocation && nextTaskInvocationIterator.hasNext();) {

                                ATaskInvocation nextTaskInvocation = (ATaskInvocation) nextTaskInvocationIterator.next();

                                final String nextTaskInvocationName = nextTaskInvocation.getTaskName().getText();

                                if (taskInvocationName.compareTo(nextTaskInvocationName) == 0) {
                                    final int nextTaskInvocationFrequency = Integer.parseInt(nextTaskInvocation.getTaskFrequency().getText());

                                    if ((modePeriod/taskInvocationFrequency) == (nextModePeriod/nextTaskInvocationFrequency))
                                        noNextTaskInvocation = false;
                                    else
                                        errorTaskFrequency(taskInvocation.getTaskName(),
                                                modeSwitch.getModeName(),
                                                nextTaskInvocation.getTaskFrequency());
                                }
                            }

                            if (noNextTaskInvocation)
                                errorNoNextTask(taskInvocation.getTaskName(), modeSwitch.getModeName());
                        }
                    }
                }
            }
        }
    }

    private static void errorFrequencyUndeclaredMode(Token token)
    {
        throw new RuntimeException("[" + token.getLine() + "," + token.getPos() + "]" +
                " Other frequency than one not allowed in mode switch to undeclared mode: " + token.getText());
    }

    private static void errorZero (Token token)
    {
        throw new RuntimeException("[" + token.getLine() + "," + token.getPos() + "]" +
                " Zero period or frequency not allowed.");
    }

    private static void errorFrequency (Token token, String modePeriod)
    {
        throw new RuntimeException("[" + token.getLine() + "," + token.getPos() + "]" +
                " Frequency: " + token.getText() +
                " does not divide mode period: " + modePeriod + ".");
    }

    private static void errorTaskFrequency (Token taskToken, Token modeSwitchToken, Token nextTaskFrequencyToken)
    {
        throw new RuntimeException("[" + taskToken.getLine() + "," + taskToken.getPos() + "]" +
                " Task: " + taskToken.getText() +
                " is invoked in mode: " + modeSwitchToken.getText() +
                " [" + modeSwitchToken.getLine() + "," + modeSwitchToken.getPos() + "]" +
                " with incompatible frequency: " + nextTaskFrequencyToken.getText() +
                " [" + nextTaskFrequencyToken.getLine() + "," + nextTaskFrequencyToken.getPos() + "].");
    }

    private static void errorNoNextTask (Token taskToken, Token modeSwitchToken)
    {
        throw new RuntimeException("[" + taskToken.getLine() + "," + taskToken.getPos() + "]" +
                " Task: " + taskToken.getText() +
                " must be invoked in mode: " + modeSwitchToken.getText() +
                " [" + modeSwitchToken.getLine() + "," + modeSwitchToken.getPos() + "].");
    }

    private static void errorTaskFrequencyEqualOne (Token modeToken)
    {
        throw new RuntimeException("[" + modeToken.getLine() + "," + modeToken.getPos() + "]" +
                " No task invocation with frequency 1 in mode: " + modeToken.getText() + ".");
    }

    public String toString()
    {
        return symbolTable.toString();
    }
}
