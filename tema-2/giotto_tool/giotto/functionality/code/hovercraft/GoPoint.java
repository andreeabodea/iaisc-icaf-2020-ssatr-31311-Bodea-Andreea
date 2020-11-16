/*
  Copyright (c) 2004 The Regents of the University of California.
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
package giotto.functionality.code.hovercraft;

import giotto.functionality.code.real_port;
import giotto.functionality.interfaces.ConditionInterface;
import giotto.functionality.table.Parameter;

import java.io.Serializable;
/**
 * @author M.A.A. Sanvido
 * @version GoPoint.java,v 1.11 2004/09/29 03:50:41 cxh Exp
 * @since Giotto 1.0.1
 */

public class GoPoint implements ConditionInterface, Serializable {

    /**
     * @see giotto.functionality.interfaces.ConditionInterface#run(Parameter)
     */
    public boolean run(Parameter p) {
        if (Hovercraft.skip_port) return Hovercraft.main.doPoint();

        //ex, ey, ea, ea2
        float  ex = ((real_port)p.getPortVariable(0)).getFloatValue();
        float  ey = ((real_port)p.getPortVariable(1)).getFloatValue();
        float  ea2 = ((real_port)p.getPortVariable(3)).getFloatValue();
        float  ter = (float)Math.sqrt(ex*ex + ey*ey);
        return (ter <=5) & (Math.abs(ea2) > 0.1);
    }

    /**
     * @see giotto.functionality.interfaces.ConditionInterface
     */
    public void overRun() {
    }

}
