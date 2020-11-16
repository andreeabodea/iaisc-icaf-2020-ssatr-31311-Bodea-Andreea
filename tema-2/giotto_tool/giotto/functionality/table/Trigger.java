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

import giotto.functionality.interfaces.TriggerInterface;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * @author Christoph Kirsch
 * @version Trigger.java,v 1.13 2004/09/29 03:51:45 cxh Exp
 * @since Giotto 1.0.1
 */
public class Trigger extends TableEntry implements Serializable {
    private static Hashtable table = null;
    private static String[] list = null;
    private static int trgs;

    public Trigger(String name, String codeName) {
        super(name, codeName, getNumberOf());

        table.put(name, this);
        list[trgs] = name;
        trgs++;
    }

    public String generateWrapperName(String name) {
        return "trigger_" + name;
    }

    public boolean isEnabled(int time, int delta, int state, int overflow) {
        return ((TriggerInterface) getCode()).isEnabled(time, delta, state, overflow);
    }

    // static methods

    public static Trigger getTrigger(String name) {
        return (Trigger) table.get(name);
    }

    public static Trigger getTrigger(int index) {
        return (Trigger) table.get(list[index]);
    }

    /**
     * Method getNumberOfTriggers.
     * @return int
     */
    public static int getNumberOf() {
        return trgs;
    }

    public static void reset() {
        table = new Hashtable();
        list = new String[256];
        trgs = 0;
    }

    public static void Save(ObjectOutputStream oos) throws Exception {
        int i = 0;
        oos.writeInt(trgs);
        while (i<trgs) {
            oos.writeObject(getTrigger(i));
            i++;
        }
    }

    public static void Load(ObjectInputStream ois) throws Exception {
        int i = 0;
        Trigger.reset();
        trgs = ois.readInt();
        while (i < trgs) {
            Trigger trg = (Trigger)ois.readObject();
            table.put(trg.getName(), trg);
            list[i] = trg.getName();
            i++;
        }
    }

    static boolean Check() {
        for (int i =0; i< trgs; i++) {
            if (!((TableEntry)table.get(list[i])).CheckCode()) return false;
        }
        return true;
    }

    public boolean checkInterface(Object obj) {
        Class[] ci = obj.getClass().getInterfaces();
        for (int i=0; i < ci.length; i++) {
            //System.out.println(ci[i].getName());
            if (ci[i].getName().equals("giotto.functionality.interfaces.TriggerInterface")) return true;
        }
        return false;
    }

}
