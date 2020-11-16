/*
  Copyright (c) 2002-2004 The Regents of the University of California.
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



/**
 * @author M.A.A. Sanvido
 * @version SystemLog.java,v 1.9 2004/09/29 03:52:32 cxh Exp
 * @since Giotto 1.0.1
 */
public class SystemLog implements LogInterface {


    /**
     * @see LogInterface#println()
     */
    public void println() {
        System.out.println();
    }

    /**
     * @see LogInterface#println(String)
     */
    public void println(String s) {
        System.out.println(s);
    }

    /**
     * @see LogInterface#println(int)
     */
    public void println(int i) {
        System.out.println(i);
    }

    /**
     * @see LogInterface#print(String)
     */
    public void print(String s) {
        System.out.print(s);
    }

    /**
     * @see LogInterface#print(int)
     */
    public void print(int i) {
        System.out.print(i);
    }

    /**
     * @see LogInterface#clear()
     */
    public void clear() {
    }
}
