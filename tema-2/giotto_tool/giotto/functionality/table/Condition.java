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

import giotto.functionality.interfaces.ConditionInterface;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * @author Christoph Kirsch
 * @version Condition.java,v 1.14 2004/09/29 03:51:26 cxh Exp
 * @since Giotto 1.0.1
 */
public class Condition extends TableEntry implements Serializable {
    private static Hashtable table = null;
    private static String[] list = null;
    private static int conds;

    private int protection;

    public Condition(String name, int protection) {
        super(name, getNumberOf());

        table.put(name, this);
        list[conds] = name;
        conds++;

        this.protection = protection;
    }

    public final String generateWrapperName(String name) {
        return "condition_" + name;
    }

    /**
     * Returns the protection.
     * @return int
     */
    public int getProtection() {
        return protection;
    }

    public boolean run() {
        return ((ConditionInterface) getCode()).run(getParameter());
    }

    // static methods

    public static Condition getCondition(String name) {
        return (Condition) table.get(name);
    }

    public static Condition getCondition(int index) {
        return (Condition) table.get(list[index]);
    }

    /**
     * Method getNumberOfTasks.
     * @return int
     */
    public static int getNumberOf() {
        return conds;
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
        conds = 0;
    }

    public static void Save(ObjectOutputStream oos) throws Exception {
        int i = 0;
        oos.writeInt(conds);
        while (i<conds) {
            oos.writeObject(getCondition(i));
            i++;
        }
    }

    public static void Load(ObjectInputStream ois) throws Exception {
        int i = 0;
        Condition.reset();
        conds = ois.readInt();
        while (i < conds) {
            Condition c = (Condition)ois.readObject();
            table.put(c.getName(), c);
            list[i] = c.getName();
            i++;
        }
    }

    static boolean Check() {
        for (int i =0; i< conds; i++) {
            if (!((TableEntry)table.get(list[i])).CheckCode()) return false;
        }
        return true;
    }

    public boolean checkInterface(Object obj) {
        Class[] ci = obj.getClass().getInterfaces();
        for (int i=0; i < ci.length; i++) {
            //System.out.println(ci[i].getName());
            if (ci[i].getName().equals("giotto.functionality.interfaces.ConditionInterface")) return true;
        }
        return false;
    }
}
