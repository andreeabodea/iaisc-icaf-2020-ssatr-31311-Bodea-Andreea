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

import giotto.functionality.interfaces.DriverInterface;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * @author Christoph Kirsch
 * @version Driver.java,v 1.14 2004/09/29 03:51:28 cxh Exp
 * @since Giotto 1.0.1
 */
public class Driver extends TableEntry implements Serializable {
    private static Hashtable table = null;
    private static String[] list = null;
    private static int drv;

    private String outputPortName = null;
    private int protection;

    public Driver(String name, int protection) {
        super(name, getNumberOf());

        table.put(name, this);
        list[drv] = name;
        drv++;

        this.protection = protection;
    }

    public Driver(String name, String codeName, int protection) {
        super(name, codeName, getNumberOf());

        table.put(name, this);
        list[drv] = name;
        drv++;

        this.protection = protection;
    }

    /**
     * Constructor Driver.
     * @param name
     * @param codeName
     * @param outputPortName
     * @param protection
     */
    public Driver(String name, String codeName, String outputPortName, int protection) {
        super(name, codeName, getNumberOf());

        table.put(name, this);
        list[drv] = name;
        drv++;

        this.outputPortName = outputPortName;
        this.protection = protection;

    }

    public String generateWrapperName(String name)
    {
        return "driver_" + name;
    }

    public void run() {
        ((DriverInterface) getCode()).run(getParameter());
    }

    /**
     * Returns the protection.
     * @return int
     */
    public int getProtection() {
        return protection;
    }

    /**
     * Sets the protection.
     * @param protection The protection to set
     */
    public void setProtection(int protection) {
        this.protection = protection;
    }

    /**
     * Returns the outputPortName.
     * @return String
     */
    public String getOutputPortName() {
        return outputPortName;
    }

    public boolean isCopyDriver() {
        return outputPortName != null;
    }

    // static methods

    public static Driver getDriver(String name) {
        return (Driver) table.get(name);
    }

    public static Driver getDriver(int index) {
        return (Driver) table.get(list[index]);
    }

    //        public static ListIterator getListIterator() {
    //                return list.listIterator();
    //        }

    /**
     * Method getNumberOfTasks.
     * @return int
     */
    public static int getNumberOf() {
        return drv;
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
        list = new String[256];
        drv = 0;
    }


    public static void Save(ObjectOutputStream oos) throws Exception {
        int i = 0;
        oos.writeInt(drv);
        while (i<drv) {
            oos.writeObject(getDriver(i));
            i++;
        }
    }

    public static void Load(ObjectInputStream ois) throws Exception {
        int i = 0;
        Driver.reset();
        drv = ois.readInt();
        while (i < drv) {
            Driver d = (Driver)ois.readObject();
            table.put(d.getName(), d);
            list[i] = d.getName();
            i++;
        }
    }

    static boolean Check() {
        for (int i =0; i< drv; i++) {
            if (!((TableEntry)table.get(list[i])).CheckCode()) return false;
        }
        return true;
    }
    public boolean checkInterface(Object obj) {
        Class[] ci = obj.getClass().getInterfaces();
        for (int i=0; i < ci.length; i++) {
            if (ci[i].getName().equals("giotto.functionality.interfaces.DriverInterface")) return true;
        }
        return false;
    }
}
