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

import giotto.functionality.table.Host;

import java.util.ListIterator;

/**
 * @author Christoph Kirsch
 * @version HGen.java,v 1.9 2004/09/29 03:52:08 cxh Exp
 * @since Giotto 1.0.1
 */
public class HGen extends CodeGen {
    private boolean pureGiotto;
    //        /private HTable hostTable;

    public HGen(SymbolTable symbolTable, boolean pureGiotto) {
        super(symbolTable, "htable", "h_table.c", "h_table.h", "h_spec.txt");

        //this.hostTable = hostTable;

        this.pureGiotto = pureGiotto;
    }

    public void emitCFileHeader() {
        emit("Header");

        emit("Include");
    }

    public void emitCFileBody() {
        ListIterator iterator;

        for (int i = 0; i < Host.getNumberOf(); i++) {
            //                for (iterator = hostTable.getListIterator(); iterator.hasNext();) {
            //                        final String hostName = (String) iterator.next();

            final Host host = Host.getHost(i);

            emit("HostInitializationWrapper", new String[] { host.getHostInitName(), host.getInitName()});
        }

        int nHosts = 0;

        if (pureGiotto) {
            emit("HostTableHeader");

            emit("Semicolon");
        } else {
            emit("HostTableHeader");

            emit("HostTableBegin");

            for (nHosts = 0; nHosts < Host.getNumberOf(); nHosts++) {
                //final String hostName = (String) iterator.next();

                if (nHosts != 0)
                    emit("TableComma");

                final Host host = Host.getHost(nHosts);

                emit(
                        "HostTableElement",
                        new String[] {
                            host.getName(),
                            host.getHostInitName(),
                            Integer.toString(host.getStartAddress()),
                            host.getIp(),
                            Integer.toString(host.getPort())});
            }

            emit("TableEnd");
        }
    }

    public void emitHFileHeader() {
        emit("Header");
    }

    public void emitHFileBody() {
        int nHosts = Host.getNumberOf();

        if (pureGiotto)
            nHosts = 0;

        emit("TableSize", new String[] { Integer.toString(nHosts)});
    }

}
