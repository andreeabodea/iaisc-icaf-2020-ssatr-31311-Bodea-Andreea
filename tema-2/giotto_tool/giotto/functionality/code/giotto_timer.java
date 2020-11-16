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

import giotto.functionality.interfaces.TriggerInterface;

import java.io.Serializable;
/**
 * @author Christoph Kirsch
 * @version giotto_timer.java,v 1.14 2004/09/29 03:49:12 cxh Exp
 * @since Giotto 1.0.1
 */
public class giotto_timer implements TriggerInterface, Serializable {
    //        private int state = 0;
    //        private int delta = 0;

    public giotto_timer() {
    }

    //        public giotto_timer(int state, int delta) {
    //                this.state = state;
    //                this.delta = delta;
    //        }

    /**
     * @see giotto.functionality.interfaces.TriggerInterface#isEnabled(int, int, int, int)
     */
    public boolean isEnabled(int time, int state, int delta, int overflow) {
        //System.out.println("trigger test : " + time + "="+ (state + delta)% overflow);
        return time >= (state + delta); // % overflow;
    }

}
