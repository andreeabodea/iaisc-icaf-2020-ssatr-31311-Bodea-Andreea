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

package giotto.giottoc.ecode;

/**
 * @author Christoph Kirsch
 * @version FixupChain.java,v 1.5 2004/09/29 03:52:41 cxh Exp
 * @since Giotto 1.0.1
 */
public class FixupChain {
    private int address = 0;
    private int unit = 0;

    private String modeName = null;

    public FixupChain(int address, int unit, String modeName) {
        this.address = address;
        this.unit = unit;

        this.modeName = modeName;
    }

    /**
     * Returns the address.
     * @return int
     */
    public int getAddress() {
        return address;
    }

    /**
     * Returns the modeName.
     * @return String
     */
    public String getModeName() {
        return modeName;
    }

    /**
     * Returns the unit.
     * @return int
     */
    public int getUnit() {
        return unit;
    }

}
