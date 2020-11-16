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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;


/**
 * @author Christoph Kirsch
 * @version TableEntry.java,v 1.7 2004/09/29 03:51:41 cxh Exp
 * @since Giotto 1.0.1
 */
public abstract class TableEntry implements Serializable {

    private static String[] FUNCTIONALITY = {"giotto.functionality.code"};
    private String name = null;
    private int index = 0;

    private String wrapperName = null;
    private String javaCodeName = null;
    private String cCodeName = null;

    private Object code = null;

    private final Parameter parameter = new Parameter();

    private static boolean makejava = false;

    protected TableEntry(String name, int index) {
        this.name = name;
        this.index = index;

        this.wrapperName = generateWrapperName(name);
        this.javaCodeName = null;
        this.cCodeName = null;
    }

    protected TableEntry(String name, String codeName, int index) {
        this.name = name;
        this.index = index;

        this.wrapperName = generateWrapperName(name);

        setCodeName(codeName);
    }

    public abstract String generateWrapperName(String name);

    private String generateJavaCodeName(int index, String name) {
        //an index i<0 indicates that the java names can not be resolved
        //at compile time
        if (index>=0) {return FUNCTIONALITY[index] +"."+ name;}
        else return "";
    }

    private String generateCCodeName(String name) {
        return name;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns getIndex.
     * @return int
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the wrapperName.
     * @return String
     */
    public String getWrapperName() {
        return wrapperName;
    }

    /**
     * Returns the code.
     * @return Object
     */
    protected Object getCode() {
        return code;
    }

    public void addParameter(Port port) {
        parameter.addParameter(port);
    }

    /**
     * Returns the parameter.
     * @return Parameter
     */
    public Parameter getParameter() {
        return parameter;
    }

    /**
     * Sets the codeName.
     * @param codeName The codeName to set
     */
    public void setCodeName(String codeName) {
        this.cCodeName = generateCCodeName(codeName);
        int i = 0;

        if (makejava) {
            while (code == null) {
                this.javaCodeName = generateJavaCodeName(i, codeName);
                try {
                    code = ClassLoader.getSystemClassLoader().loadClass(this.javaCodeName).newInstance();
                } catch (ClassNotFoundException e) {
                    code = null;
                    i++;
                    if (i == FUNCTIONALITY.length) throw new RuntimeException(javaCodeName + " not found.");
                } catch (Exception e) {
                    code = null; System.out.println(e.toString());
                    throw new RuntimeException(javaCodeName + " could not be instantiated.");
                }
            }
            if (!checkInterface(code)) {throw new RuntimeException(javaCodeName + " wrong interface.");}
        } else this.javaCodeName = generateJavaCodeName(-1, codeName);

    }
    public abstract boolean checkInterface(Object obj);
    /**
     * Returns the codeName.
     * @return String
     */
    public String getCCodeName() {
        return cCodeName;
    }

    /**
     * Returns the javaCodeName.
     * @return String
     */
    public String getJavaCodeName() {
        return javaCodeName;
    }

    public static void enableClassLoader(boolean on, String[] packages) {
        makejava = on;
        FUNCTIONALITY = packages;
    }

    public static void saveECode(String file) throws Exception{
        FileOutputStream fos;
        ObjectOutputStream oos;
        if (file != null) {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            Ecode.Save(oos);oos.flush();
            Port.Save(oos); oos.flush();
            Driver.Save(oos);oos.flush();
            Condition.Save(oos);oos.flush();
            Task.Save(oos);oos.flush();
            Trigger.Save(oos);oos.flush();
            Host.Save(oos);oos.flush();
            Network.Save(oos);oos.flush();

            oos.close();
            fos.close();
        }
    }

    public static void loadECode(String file) throws Exception {
        FileInputStream fis;
        ObjectInputStream ois;
        if (file != null) {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            Ecode.Load(ois);
            Port.Load(ois);
            Driver.Load(ois);
            Condition.Load(ois);
            Task.Load(ois);
            Trigger.Load(ois);
            Host.Load(ois);
            Network.Load(ois);

            ois.close();
            fis.close();
        }
    }

    public static void loadRemoteECode(String url) throws Exception {

        URL ecdfile = new URL(url);
        InputStream is = ecdfile.openStream();
        java.io.ObjectInputStream ois = new ObjectInputStream(is);

        Ecode.Load(ois);
        Port.Load(ois);
        Driver.Load(ois);
        Condition.Load(ois);
        Task.Load(ois);
        Trigger.Load(ois);
        Host.Load(ois);
        Network.Load(ois);

        ois.close();
        is.close();
    }

    boolean CheckCode() {
        return code != null;
    }

    public static boolean CheckTables() {
        return (Ecode.getNumberOf() > 0) && Port.Check() && Driver.Check() &&
            Condition.Check() && Task.Check() & Trigger.Check();
    }

    public static String Dump(){return "";}

    public static String DumpAll() {
        String s = "";
        s += Port.Dump();
        s += Driver.Dump();
        s += Condition.Dump();
        s += Task.Dump();
        s += Trigger.Dump();
        s += Host.Dump();
        //s += Network.Dump();
        return s;
    }


}
