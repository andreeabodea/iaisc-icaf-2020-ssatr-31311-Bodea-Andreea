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
import giotto.giottoc.node.ATaskInvocation;


/**

This class computes the number of units (smallest time unit) for each
Giotto mode.

@author Christoph Kirsch
@version ModeUnit.java,v 1.8 2004/09/29 03:52:14 cxh Exp
@since Giotto 1.0.1
*/
public class ModeUnit extends DepthFirstAdapter
{
    public int nUnits = 1;
    public int modePeriod;

    public void inAModeDeclaration(AModeDeclaration node)
    {
        modePeriod = Integer.parseInt(node.getModePeriod().getText());
    }

    public static int gcd(int a, int b) {
        if (b == 0)
            return a;
        else
            return gcd(b, a % b);
    }

    public static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    public void outAActuatorUpdate(AActuatorUpdate node)
    {
        final int frequency = Integer.parseInt(node.getActuatorFrequency().getText());

        nUnits = lcm(nUnits, frequency);
    }

    public void outAModeSwitch(AModeSwitch node)
    {
        final int frequency = Integer.parseInt(node.getModeSwitchFrequency().getText());

        nUnits = lcm(nUnits, frequency);
    }

    public void outATaskInvocation(ATaskInvocation node)
    {
        final int frequency = Integer.parseInt(node.getTaskFrequency().getText());

        nUnits = lcm(nUnits, frequency);
    }
}
