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

import giotto.functionality.table.Network;

/**
 * This class implements code generation of the network annotations.
 * A network annotation defines a unique network id etc.
 *
 * @author Christoph Kirsch
 * @version NGen.java,v 1.8 2004/09/29 03:52:16 cxh Exp
 * @since Giotto 1.0.1
 */
public class NGen extends CodeGen {
    private boolean pureGiotto;

    //private NTable networkTable;

    public NGen(SymbolTable symbolTable, boolean pureGiotto) {
        super(symbolTable, "ntable", "n_table.c", "n_table.h", "n_spec.txt");

        //this.networkTable = networkTable;

        this.pureGiotto = pureGiotto;
    }

    public void emitCFileHeader() {
        emit("Header");

        emit("Include");
    }

    public void emitCFileBody() {
        //                ListIterator iterator;

        for (int i=0; i < Network.getNumberOf(); i++) {
            //                        final String networkName = (String) iterator.next();

            final Network network = Network.getNetwork(i);

            emit("NetworkInitializationWrapper", new String[] { network.getNetworkInitName(), network.getInitName() });
        }

        int nNetworks = 0;

        if (pureGiotto) {
            emit("NetworkTableHeader");

            emit("Semicolon");
        } else {
            emit("NetworkTableHeader");

            emit("NetworkTableBegin");

            for (nNetworks = 0; nNetworks < Network.getNumberOf(); nNetworks++) {
                //final String networkName = (String) iterator.next();

                if (nNetworks != 0)
                    emit("TableComma");

                final Network network = Network.getNetwork(nNetworks);

                emit("NetworkTableElement", new String[] { network.getName(), network.getNetworkInitName() });
            }

            emit("TableEnd");
        }
    }

    public void emitHFileHeader() {
        emit("Header");
    }

    public void emitHFileBody() {
        int nNetworks = Network.getNumberOf();

        if (pureGiotto)
            nNetworks = 0;

        emit("TableSize", new String[] { Integer.toString(nNetworks)});
    }

}
