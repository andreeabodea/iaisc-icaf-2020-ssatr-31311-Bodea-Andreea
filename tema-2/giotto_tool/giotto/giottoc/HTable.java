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
import giotto.giottoc.analysis.DepthFirstAdapter;
import giotto.giottoc.node.AHostDeclaration;
import giotto.giottoc.node.AIP;

/**

This class implements code generation of the host annotations.
A host annotation defines a unique host id etc.

@author Christoph Kirsch
@version HTable.java,v 1.9 2004/09/29 03:52:10 cxh Exp
@since Giotto 1.0.1
*/
public class HTable extends Wrapper {
    private boolean pureGiotto;

    //private final ArrayList orderedHosts = new ArrayList();
    //private final Hashtable hostTable = new Hashtable();

    public HTable(SymbolTable symbolTable, boolean pureGiotto) {
        super(symbolTable);

        this.pureGiotto = pureGiotto;
    }

    // Action code

    private class GenHostPriorityAllocation extends DepthFirstAdapter {
        private String hostName;

        public GenHostPriorityAllocation(String hostName) {
            this.hostName = hostName;
        }

        // FIXME: Add priorities.
    }

    private String ipToString(AIP node) {
        return node.getOctet1().getText()
            + "."
            + node.getOctet2().getText()
            + "."
            + node.getOctet3().getText()
            + "."
            + node.getOctet4().getText();
    }

    public void inAHostDeclaration(AHostDeclaration node) {
        final String hostName = node.getHostName().getText();

        node.apply(new GenHostPriorityAllocation(hostName));

        if (!Host.isDeclared(hostName)) {
            //orderedHosts.add(hostName);

            final String hostIP = ipToString((AIP) node.getHostIP());
            final String hostPort = node.getHostPort().getText();
            final String initName = generateInitName(hostName);
            final String hostInitName = generateHostName(initName);

            //hostTable.put(hostName, new Host(hostName, hostIP, hostPort));
            final Host host = new Host(hostName, hostIP, hostPort, initName, hostInitName);
        } else
            throw new RuntimeException("Ambiguous host names.");
    }

    //        public class Host {
    //                private String name;
    //                private String ip;
    //                private String port;
    //                private int startAddress = 0;
    //                private String initName;
    //                private String hostInitName;
    //
    //                public Host(String name, String ip, String port) {
    //                        this.name = name;
    //                        this.ip = ip;
    //                        this.port = port;
    //
    //                        this.initName = generateInitName(name);
    //                        this.hostInitName = generateHostName(initName);
    //                }
    //
    //                /**
    //                 * Returns the ip.
    //                 * @return String
    //                 */
    //                public String getIp() {
    //                        return ip;
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
    //                public String getHostInitName() {
    //                        return hostInitName;
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
    //                /**
    //                 * Returns the startAddress.
    //                 * @return int
    //                 */
    //                public int getStartAddress() {
    //                        return startAddress;
    //                }
    //
    //                /**
    //                 * Returns the port.
    //                 * @return String
    //                 */
    //                public String getPort() {
    //                        return port;
    //                }
    //
    //        }

    //        /**
    //         * Returns the host.
    //         * @return Host
    //         */
    //        public Host getHost(String hostName) {
    //                return (Host) hostTable.get(hostName);
    //        }
    //
    //        public int getNumberOf() {
    //                return hostTable.size();
    //        }
    //
    //        public ListIterator getListIterator() {
    //                return orderedHosts.listIterator();
    //        }
    //
    //        public void addStartAddress(String hostName, int startAddress) {
    //                ((Host) hostTable.get(hostName)).startAddress = startAddress;
    //        }
    //
    private String generateHostName(String hostName) {
        return generateName("host", hostName);
    }

}
