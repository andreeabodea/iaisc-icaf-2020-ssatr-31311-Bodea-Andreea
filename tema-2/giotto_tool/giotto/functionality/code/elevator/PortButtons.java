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

import giotto.functionality.interfaces.PortVariable;

import java.io.Serializable;

/**
 * @author M.A.A. Sanvido
 * @version PortButtons.java,v 1.16 2004/10/01 01:09:34 cxh Exp
 * @since Giotto 1.0.1
 */
public class PortButtons implements PortVariable, Serializable {

    boolean [] button;

    public PortButtons () {
        button = new boolean[Elevator.MAX+1];
    }

    /**
     * @see giotto.functionality.interfaces.PortVariable
     */
    public boolean[] getButtonValue() {
        return this.button;
    }

    /**
     * @see giotto.functionality.interfaces.PortVariable
     */
    public void setValue(boolean[] value) {
        int i =0;
        int j = this.button.length;
        for (i = 0; i < j; i++) {button[i] = value[i];}
    }

    public void copyValueFrom(PortVariable to) {
        int i = 0;
        int j = this.button.length;
        for (i = 0; i < j; i++) ((PortButtons)to).button[i] = this.button[i];
    }

}


