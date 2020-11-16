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

package giotto.functionality.table;

import giotto.functionality.interfaces.PortVariable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * @author Christoph Kirsch
 * @version Port.java,v 1.14 2004/09/29 03:51:38 cxh Exp
 * @since Giotto 1.0.1
 */
public class Port extends TableEntry implements Serializable {
    private static Hashtable table = null;
    private static Port[] list = null;
    private static int ports = 0;

    private String type;

    public Port(String name, String type) {
        super(name, type, getNumberOf());

        table.put(name, this);
        list[ports] = this;
        ports++;
        this.type = type;
    }

    public String generateWrapperName(String name) {
        return "port_" + name;
    }

    /**
     * Returns the type.
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the code.
     * @return Object
     */
    public PortVariable getPortVariable() {
        return (PortVariable) getCode();
    }

    // static methods

    public static Port getPort(String name) {
        return (Port) table.get(name);
    }

    public static Port getPort(int index) {
        return list[index];
    }

    //        public static ListIterator getListIterator() {
    //                return list.listIterator();
    //        }

    /**
     * Method getNumberOfTasks.
     * @return int
     */
    public static int getNumberOf() {
        return ports;
    }

    /**
     * Return true if the method is declared.
     * @param name The name of the task.
     * @return boolean true if the method is declared.
     */
    public static boolean isDeclared(String name) {
        return table.containsKey(name);
    }

    public static void reset() {
        table = new Hashtable();
        list = new Port[256];
        ports = 0;
    }

    public static void Save(ObjectOutputStream oos) throws Exception {
        int i = 0;
        oos.writeInt(ports);
        while (i<ports) {
            oos.writeObject(getPort(i));
            i++;
        }

    }

    public static void Load(ObjectInputStream ois) throws Exception {
        int i = 0;
        Port.reset();
        ports = ois.readInt();
        while (i < ports) {
            Port p = (Port)ois.readObject();
            table.put(p.getName(), p);
            list[i] = p;
            i++;
        }
    }

    static boolean Check() {
        for (int i =0; i< ports; i++) {
            if (!((TableEntry)list[i]).CheckCode()) return false;
        }
        return true;
    }

    public boolean checkInterface(Object obj) {
        Class[] ci = obj.getClass().getInterfaces();
        for (int i=0; i < ci.length; i++) {
            if (ci[i].getName().equals("giotto.functionality.interfaces.PortVariable")) return true;
        }
        return false;
    }

    public static String Dump() {
        String s = "Port: \n";
        for (int i = 0; i < Port.getNumberOf(); i++) {
            Port p = Port.getPort(i);
            s += "\t"+p.getName() + " " + p.getType() + "\n";
        }
        return s;
    }

}
