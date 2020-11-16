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

import giotto.functionality.interfaces.TaskInterface;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * @author Christoph Kirsch
 * @version Task.java,v 1.14 2004/09/29 03:51:43 cxh Exp
 * @since Giotto 1.0.1
 */
public class Task extends TableEntry implements Serializable {
    private static Hashtable table = null;
    private static String[] list = null;
    private static int tsks;

    private String modeName = null;

    public Task(String name) {
        super(name, getNumberOf());

        table.put(name, this);
        list[tsks] = name;
        tsks++;;
    }

    /**
     * Constructor Task.
     * @param name
     * @param modeName
     */
    public Task(String name, String modeName) {
        super(name, getNumberOf());

        table.put(name, this);
        list[tsks] = name;
        tsks++;

        this.modeName = modeName;
    }

    public String generateWrapperName(String name) {
        return "task_" + name;
    }

    public boolean isMessage() {
        return modeName != null;
    }

    public void run() throws StopException{
        ((TaskInterface) getCode()).run(getParameter());
    }

    // static methods

    public static Task getTask(String name) {
        return (Task) table.get(name);
    }

    public static Task getTask(int index) {
        return (Task) table.get(list[index]);
    }

    //        public static ListIterator getListIterator() {
    //                return list.listIterator();
    //        }
    //
    /**
     * Method getNumberOfTasks.
     * @return int
     */
    public static int getNumberOf() {
        return tsks;
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
        tsks = 0;
    }

    public static void Save(ObjectOutputStream oos) throws Exception {
        int i = 0;
        oos.writeInt(tsks);
        while (i<tsks) {
            oos.writeObject(getTask(i));
            i++;
        }
    }

    public static void Load(ObjectInputStream ois) throws Exception {
        int i = 0;
        Task.reset();
        tsks = ois.readInt();
        while (i < tsks) {
            Task tsk = (Task)ois.readObject();
            table.put(tsk.getName(), tsk);
            list[i] = tsk.getName();
            i++;
        }
    }

    static boolean Check() {
        for (int i =0; i< tsks; i++) {
            if (!((TableEntry)table.get(list[i])).CheckCode()) return false;
        }
        return true;
    }

    public boolean checkInterface(Object obj) {
        Class[] ci = obj.getClass().getInterfaces();
        for (int i=0; i < ci.length; i++) {
            if (ci[i].getName().equals("giotto.functionality.interfaces.TaskInterface")) return true;
        }
        return false;
    }

    public static String Dump() {
        String s = "Task: \n";
        for (int i = 0; i < Task.getNumberOf(); i++) {
            Task p = Task.getTask(i);
            s += "\t"+p.getName() + " " + p.getWrapperName() + " " + p.getCCodeName() + " " +p.getJavaCodeName() +"\n";
        }
        return s;
    }


}
