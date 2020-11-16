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
import giotto.giottoc.analysis.DepthFirstAdapter;
import giotto.giottoc.node.ANetworkDeclaration;


/**
 * @author Christoph Kirsch
 * @version NTable.java,v 1.10 2004/09/29 03:52:18 cxh Exp
 * @since Giotto 1.0.1
 */
public class NTable extends Wrapper {
    private boolean pureGiotto;

    //private final ArrayList orderedNetworks = new ArrayList();
    //private final Hashtable networkTable = new Hashtable();

    public NTable(SymbolTable symbolTable, boolean pureGiotto) {
        super(symbolTable);

        this.pureGiotto = pureGiotto;
    }

    // Action code

    private class GenNetworkTimeslotAllocation extends DepthFirstAdapter {
        private String networkName;

        public GenNetworkTimeslotAllocation(String networkName) {
            this.networkName = networkName;
        }

        // FIXME: Add priorities.
    }

    public void inANetworkDeclaration(ANetworkDeclaration node) {
        if (!pureGiotto) {
            final String networkName = node.getNetworkName().getText();

            node.apply(new GenNetworkTimeslotAllocation(networkName));

            if (!Network.isDeclared(networkName)) {
                //orderedNetworks.add(networkName);
                //networkTable.put(networkName, new Network(networkName));

                final Network net = new Network(networkName);

            } else
                throw new RuntimeException("Ambiguous network names.");
        }
    }

    //        public class Network {
    //                private String name;
    //                private String initName;
    //                private String networkInitName;
    //
    //                public Network(String name) {
    //                        this.name = name;
    //
    //                        this.initName = generateInitName(name);
    //                        this.networkInitName = generateNetworkName(initName);
    //                }
    //
    //                /**
    //                 * Returns the initName.
    //                 * @return String
    //                 */
    //                public String getInitName() {
    //                        return initName;
    //                }
    //
    //                /**
    //                 * Returns the hostInitName.
    //                 * @return String
    //                 */
    //                public String getNetworkInitName() {
    //                        return networkInitName;
    //                }
    //
    //                /**
    //                 * Returns the name.
    //                 * @return String
    //                 */
    //                public String getName() {
    //                        return name;
    //                }
    //
    //        }
    //
    //        /**
    //         * Returns the network.
    //         * @return Network
    //         */
    //        public Network getNetwork(String name) {
    //                return (Network) networkTable.get(name);
    //        }
    //
    //        public int getNumberOf() {
    //                return networkTable.size();
    //        }
    //
    //        public ListIterator getListIterator() {
    //                return orderedNetworks.listIterator();
    //        }
    //
    //        private String generateNetworkName(String networkName) {
    //                return generateName("network", networkName);
    //        }
    //
}
