/*
  Copyright (c) 2002-2004 The Regents of the University of California.
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

/**
 * @author M.A.A. Sanvido
 * @version Ecode.java,v 1.10 2004/09/29 03:51:30 cxh Exp
 * @since Giotto 1.0.1
 */
public class Ecode implements Serializable {

    private static int maxEcode = 0;
    private static int maxUnits = 1;
    private static int minUnitPeriod = -1;

    private static Ecode[] prog;


    public static final int nopCode = 0;
    public static final int futureCode = 1;
    public static final int callCode = 2;
    public static final int scheduleCode = 3;
    public static final int ifCode = 4;
    public static final int jumpCode = 5;
    public static final int returnCode = 6;

    private int address = 0;

    private int opcode = 0;
    private int arg1 = -1;
    private int arg2 = -1;
    private int arg3 = -1;

    private String comment = "";

    public Ecode(int adr, int op, int a1, int a2, int a3, String cm) {
        prog[adr] = this;
        this.address = adr;
        this.opcode = op;
        this.arg1 = a1;
        this.arg2 = a2;
        this.arg3 = a3;
        this.comment = cm;
    }

    public static void reset() {
        prog = null;
    }


    public String toString() {
        String s = ""+address+": ";

        switch (opcode) {
        case nopCode :      s = s+"nop      "; break;
        case futureCode :   s = s+"future   ("+Trigger.getTrigger(arg1).getJavaCodeName()+","+arg2+","+arg3+")"; break;
        case callCode :     s = s+"call     ("+Driver.getDriver(arg1).getJavaCodeName()+")"; break;
        case scheduleCode : s = s+"schedule ("+Task.getTask(arg1).getJavaCodeName()+","+arg2+","+(arg3 & 0xFFF)+","+(arg3>>12)+")"; break;
        case ifCode :       s = s+"if       ("+Condition.getCondition(arg1).getJavaCodeName()+","+arg2+","+arg3+")"; break;
        case jumpCode :     s = s+"jump     ("+arg1+")"; break;
        case returnCode :   s = s+"return   "; break;
        default :                ; break;
        }

        return s + "       // " + this.comment; //+ comment;

    }

    /**
     * Returns the address.
     * @return int
     */
    public int getAddress() {
        return address;
    }

    /**
     * Returns the opcode.
     * @return int
     */
    public int getOpcode() {
        return opcode;
    }

    /**
     * Returns the arg1.
     * @return int
     */
    public int getArg1() {
        return arg1;
    }

    /**
     * Returns the arg2.
     * @return int
     */
    public int getArg2() {
        return arg2;
    }

    /**
     * Returns the arg3.
     * @return int
     */
    public int getArg3() {
        return arg3;
    }

    /**
     * Returns the comment.
     * @return String
     */
    public String getComment() {
        return comment;
    }
    //Serializable


    //Static Methods
    /**
     * Returns the instruction at address.
     * @return Instruction
     */
    public static Ecode get(int address) {
        return prog[address];
    }

    /**
     * Returns the number of instructions.
     * @return int
     */
    public static int getNumberOf() {
        if (prog == null) return 0;
        return prog.length;
    }

    /**
     * Returns the maxUnits.
     * @return int
     */
    public static int getMaxUnits() {
        return maxUnits;
    }

    /**
     * Returns the minUnitPeriod.
     * @return int
     */
    public static int getMinUnitPeriod() {
        return minUnitPeriod;
    }

    public static void Init(int ME, int MU, int MUP) {
        maxEcode = ME;
        maxUnits = MU;
        minUnitPeriod = MUP;
        prog = new Ecode[maxEcode];
    }

    public static void Save(ObjectOutputStream oos) throws Exception {
        int i = 0;
        oos.writeInt(maxEcode);
        oos.writeInt(maxUnits);
        oos.writeInt(minUnitPeriod);
        while (i<maxEcode) {
            oos.writeObject(Ecode.get(i));
            i++;
        }
    }

    public static void Load(ObjectInputStream ois) throws Exception {
        int i = 0;
        maxEcode = ois.readInt();
        maxUnits = ois.readInt();
        minUnitPeriod = ois.readInt();
        Ecode.Init(maxEcode, maxUnits, minUnitPeriod);
        while (i<maxEcode) {
            Ecode.prog[i] = (Ecode)ois.readObject();
            i++;
        }
    }

}
