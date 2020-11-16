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
 * @version Error.java,v 1.9 2004/09/29 03:50:19 cxh Exp
 * @since Giotto 1.0.1
 */
public class Error implements TaskInterface, Serializable {

    public float LimitAngle(float angle) {
        while (angle > Math.PI) angle -= (float)Math.PI*2;
        while (angle < -Math.PI) angle += (float)Math.PI*2;
        return angle;
    }


    public void run(Parameter p) {
        Hovercraft.main.computeErrors();

        if (Hovercraft.skip_port) return;
        //                p0x, p0y, p0a, t0x, t0y, t0a, ex ,ey, ea, ea2

        float  p0x = ((real_port)p.getPortVariable(0)).getFloatValue();
        float  p0y = ((real_port)p.getPortVariable(1)).getFloatValue();
        float  p0a = ((real_port)p.getPortVariable(2)).getFloatValue();
        float  t0x = ((real_port)p.getPortVariable(3)).getFloatValue();
        float  t0y = ((real_port)p.getPortVariable(4)).getFloatValue();
        float  t0a = ((real_port)p.getPortVariable(5)).getFloatValue();
        float  ex = t0x - p0x;
        float  ey = t0y - p0y;

        float ldir = (float)Math.atan2(ey, ex);

        float ea = LimitAngle(ldir - p0a);
        float ea2 = LimitAngle(t0a - p0a);

        ((real_port)p.getPortVariable(6)).setFloatValue(ex);
        ((real_port)p.getPortVariable(7)).setFloatValue(ey);
        ((real_port)p.getPortVariable(8)).setFloatValue(ea);
        ((real_port)p.getPortVariable(9)).setFloatValue(ea2);

        System.out.println(ex+","+ey+","+ea+","+ea2);

    }

}
