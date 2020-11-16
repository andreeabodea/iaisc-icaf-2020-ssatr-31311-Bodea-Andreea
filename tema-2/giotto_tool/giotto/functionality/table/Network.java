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
import java.util.Hashtable;

/**
 * @author M.A.A. Sanvido
 * @version Network.java,v 1.6 2004/09/29 03:51:34 cxh Exp
 * @since Giotto 1.0.1
 */

public class Network extends TableEntry implements Serializable {
    private static Hashtable table = null;
    private static String[] list = null;
    private static int networks;

    private String name;
    private String initName;
    private String networkInitName;

    public Network(String name) {
        super(name, networks);

        this.name = name;
        this.initName = generateInitName(name);
        this.networkInitName = generateWrapperName(initName);

        table.put(name, this);
        list[networks] = name;
        networks++;
    }


    protected static String generateInitName(String name) {
        return "init_" + name;
    }

    public String generateWrapperName(String name) {
        return "network_" + name;
    }

    public String getInitName() {
        return initName;
    }

    public String getNetworkInitName() {
        return networkInitName;
    }

    public String getName() {
        return name;
    }


    // static methods

    public static boolean isDeclared(String name) {
        return table.containsKey(name);
    }

    public static Network getNetwork(String name) {
        return (Network) table.get(name);
    }

    public static Network getNetwork(int index) {
        return (Network) table.get(list[index]);
    }

    public static int getNumberOf() {
        return networks;
    }

    public static void reset() {
        table = new Hashtable();
        list = new String[256];
        networks = 0;
    }

    public static void Save(ObjectOutputStream oos) throws Exception {
        int i = 0;
        oos.writeInt(networks);
        while (i<networks) {
            oos.writeObject(getNetwork(i));
            i++;
        }
    }

    public static void Load(ObjectInputStream ois) throws Exception {
        int i = 0;
        Network.reset();
        networks = ois.readInt();
        while (i < networks) {
            Network h = (Network)ois.readObject();
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
        String s = "Netwok:\n";
        for (int i = 0; i < Network.getNumberOf(); i++) {
            Network p = Network.getNetwork(i);
            s += "\t"+p.getName() + " " + p.getNetworkInitName() + "\n";
        }
        return s;
    }


}

