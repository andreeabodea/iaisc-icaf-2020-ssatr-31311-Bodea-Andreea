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

package giotto.functionality.code;

import giotto.functionality.interfaces.PortVariable;

import java.io.Serializable;

/**
 * @author Christoph Kirsch
 * @version string_port.java,v 1.12 2004/09/29 03:49:27 cxh Exp
 * @since Giotto 1.0.1
 */

public class string_port implements PortVariable, Serializable {
    // I Prefer this way, since the change in state of this object occurs by invoking its method.
    public void copyValueFrom(PortVariable source) {
        // Here we assume that the cast works...  GiottoC
        // should guarauntee as a type check that this is the case.
        _value = ((string_port)source).getStringValue();
    }
    // Nice and efficient, and we don't have to implement the other methods like setBooleanValue which don't make sense.
    public String getStringValue() {
        return _value;
    }
    // It is perfectly OK that the other classes don't have these Methods...
    public void setStringValue(String value) {
        _value = value;
    }
    private String _value;

    public String toString() {
        return _value;
    }

}

