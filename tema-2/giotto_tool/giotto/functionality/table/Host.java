/*
  Copyright (c) 2003-2004 The Regents of the University of California.
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
package giotto.functionality.table;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Hashtable;

/**
 * @author M.A.A. Sanvido
 * @version Host.java,v 1.6 2004/09/29 03:51:32 cxh Exp
 * @since Giotto 1.0.1
 */

public class Host extends TableEntry implements Serializable {
    private static Hashtable table = null;
    private static String[] list = null;
    private static int hosts;

    private int startAddress = 0;
    private String ip; private InetAddress inetaddress;
    private int port;
    private String initName;
    private String hostInitName;

    public Host(
            String name,
            String ip,
            String port,
            String initName,
            String hostInitName) {
        super(name, getNumberOf());
        this.ip = ip;

        try {this.inetaddress = InetAddress.getByName(ip);
        } catch (Exception e) {}


        this.port = Integer.parseInt(port);
        this.initName = initName;
        this.hostInitName = hostInitName;

        table.put(name, this);
        list[hosts] = name;
        hosts++;
    }

    public String generateWrapperName(String name) {
        return "host_" + name;
    }

    /**
     * Returns the ip.
     * @return String
     */
    public String getIp() {
        return ip;
    }

    public InetAddress getInetAddress() {
        return inetaddress;
    }

    /**
     * Method getPort.
     * @return String
     */
    public int getPort() {
        return port;
    }

    public void addStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    /**
     * Returns the initName.
     * @return String
     */
    public String getInitName() {
        return initName;
    }

    /**
     * Returns the hostInitName.
     * @return String
     */
    public String getHostInitName() {
        return hostInitName;
    }

    /**
     * Returns the startAddress.
     * @return int
     */
    public int getStartAddress() {
        return startAddress;
    }

    // static methods

    public static boolean isDeclared(String name) {
        return table.containsKey(name);
    }

    public static Host getHost(String hostName) {
        return (Host) table.get(hostName);
    }

    public static Host getHost(int index) {
        return (Host) table.get(list[index]);
    }

    public boolean islocal() {
        try {
            return this.ip.equals(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception e) {}
        return false;
    }

    /**
     * Method getNumberOf.
     * @return int
     */
    public static int getNumberOf() {
        return hosts;
    }

    public static void reset() {
        table = new Hashtable();
        list = new String[256];
        hosts = 0;
    }

    public static void Save(ObjectOutputStream oos) throws Exception {
        int i = 0;
        oos.writeInt(hosts);
        while (i < hosts) {
            oos.writeObject(getHost(i));
            i++;
        }
    }

    public static void Load(ObjectInputStream ois) throws Exception {
        int i = 0;
        Host.reset();
        hosts = ois.readInt();
        while (i < hosts) {
            Host h = (Host) ois.readObject();
            table.put(h.getName(), h);
            list[i] = h.getName();
            i++;
        }
    }

    /* no interface is needed */
    public boolean checkInterface(Object obj) {
        return true;
    }

    public static String Dump() {
        String s = "Host: \n";
        for (int i = 0; i < Host.getNumberOf(); i++) {
            Host p = Host.getHost(i);
            s += "\t"+p.getName() + " " + p.getIp()+":"+p.getPort()+ " "+ p.getStartAddress() + "\n";
        }
        return s;
    }

}
