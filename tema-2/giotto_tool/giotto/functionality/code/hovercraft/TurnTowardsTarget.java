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
import giotto.functionality.interfaces.TaskInterface;
import giotto.functionality.table.Parameter;

import java.io.Serializable;

/**
 * @author M.A.A. Sanvido
 * @version TurnTowardsTarget.java,v 1.7 2004/09/29 03:51:00 cxh Exp
 * @since Giotto 1.0.1
 */
public class TurnTowardsTarget implements TaskInterface, Serializable {

    public void run(Parameter p) {
        if (Hovercraft.skip_port) {
            Hovercraft.main.PointControl();
            //e0x, e0y, e0a, e0a2, turn, gust
        } else {
            float  ea2 = ((real_port)p.getPortVariable(3)).getFloatValue();
            float fp = 0;
            float tp = Hovercraft.PointPControl(ea2);
            ((real_port)p.getPortVariable(4)).setFloatValue(tp);
            ((real_port)p.getPortVariable(5)).setFloatValue(fp);
        }
    }

}
