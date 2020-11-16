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
package giotto.functionality.code.elevator;

import giotto.functionality.code.int_port;
import giotto.functionality.interfaces.PortVariable;

import java.io.Serializable;

/**
 * @author M.A.A. Sanvido
 * @version PortDoor.java,v 1.14 2004/10/01 01:09:37 cxh Exp
 * @since Giotto 1.0.1
 */
public class PortDoor extends int_port implements PortVariable, Serializable {


    public PortDoor() {
        setIntValue(0);
    }

    public static void OpenClose(PortDoor d) {
        int s = d.getIntValue();
        if (s >=0) Elevator.Close();
        else Elevator.Open();
    }

    public static void Init(PortDoor d) {
        d.setIntValue(0);
    }


}
