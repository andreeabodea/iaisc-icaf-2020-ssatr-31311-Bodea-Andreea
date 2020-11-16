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
import giotto.giottoc.node.AModeDeclaration;
import giotto.giottoc.node.ATaskDeclaration;
import giotto.giottoc.node.ATaskInvocation;
import giotto.giottoc.node.ATaskWcetAnnotation;
import giotto.giottoc.node.Token;

/**

This class checks whether the input program is 'time-safe'.

@author Christoph Kirsch
@version TimeSafetyChecker.java,v 1.9 2004/09/29 03:52:34 cxh Exp
@since Giotto 1.0.1
*/
public class TimeSafetyChecker extends DepthFirstAdapter {
    private int modePeriod = 0;
    private double modeUtilization = 0.0;

    private boolean noWcet = true;

    private SymbolTable symbolTable;
    private boolean pureGiotto;

    public TimeSafetyChecker(SymbolTable symbolTable, boolean pureGiotto) {
        this.symbolTable = symbolTable;

        this.pureGiotto = pureGiotto;
    }

    public void inAModeDeclaration(AModeDeclaration node) {
        modePeriod = Integer.parseInt(node.getModePeriod().getText());

        modeUtilization = 0.0;
    }

    public void outATaskInvocation(ATaskInvocation node) {
        final String taskName = node.getTaskName().getText();
        final int frequency = Integer.parseInt(node.getTaskFrequency().getText());

        final ATaskDeclaration taskDeclaration = (ATaskDeclaration) symbolTable.tasks.get(taskName);

        int wcet = 0;

        if (!pureGiotto && taskDeclaration.getTaskWcetAnnotation() != null) {
            final ATaskWcetAnnotation taskWcetAnnotation =
                (ATaskWcetAnnotation) taskDeclaration.getTaskWcetAnnotation();

            wcet = Integer.parseInt(taskWcetAnnotation.getTime().getText());

            noWcet = false;
        }

        modeUtilization = modeUtilization + ((double) wcet / (modePeriod / frequency));
    }

    public void outAModeDeclaration(AModeDeclaration node) {
        if (modeUtilization > 1.0)
            errorNotTimeSafe(node.getModeName());

        //else if (!noWcet)
        //        System.out.println("Mode " + node.getModeName().getText() + ", utilization: " + modeUtilization);

        modePeriod = 0;

        modeUtilization = 0.0;
    }

    private void errorNotTimeSafe(Token modeToken) {
        throw new RuntimeException(
                "["
                + modeToken.getLine()
                + ","
                + modeToken.getPos()
                + "]"
                + " Mode "
                + modeToken.getText()
                + " not time safe, utilization: "
                + modeUtilization);
    }

    public String toString() {
        return symbolTable.toString();
    }
}
