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

package giotto.giottoc.ecode;


import giotto.functionality.table.Ecode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.ListIterator;

/**
 * @author Christoph Kirsch
 * @version Instruction.java,v 1.9 2004/09/29 03:52:43 cxh Exp
 * @since Giotto 1.0.1
 */
public class Instruction {
    private static ArrayList program = null;
    private static Hashtable taskInvocationAddresses = new Hashtable();

    private static int maxUnits = 1;
    private static int minUnitPeriod = -1;

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

    public Instruction(int opcode) {
        this.address = getNumberOf();

        this.opcode = opcode;

        program.add(this);
    }

    public Instruction(int opcode, String comment) {
        this.address = getNumberOf();

        this.opcode = opcode;

        this.comment = comment;

        program.add(this);
    }

    public Instruction(int opcode, int arg1) {
        this.address = getNumberOf();

        this.opcode = opcode;
        this.arg1 = arg1;

        program.add(this);
    }

    public Instruction(int opcode, int arg1, String comment) {
        this.address = getNumberOf();

        this.opcode = opcode;
        this.arg1 = arg1;

        this.comment = comment;

        program.add(this);
    }

    public Instruction(int opcode, int arg1, int arg2, String comment) {
        this.address = getNumberOf();

        this.opcode = opcode;
        this.arg1 = arg1;
        this.arg2 = arg2;

        this.comment = comment;

        program.add(this);
    }

    public Instruction(int opcode, int arg1, int arg2, int arg3) {
        this.address = getNumberOf();

        this.opcode = opcode;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;

        program.add(this);
    }

    public Instruction(int opcode, int arg1, int arg2, int arg3, String comment) {
        this.address = getNumberOf();

        this.opcode = opcode;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;

        this.comment = comment;

        program.add(this);
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

    public void fixupFuture(int address) {
        if (opcode == futureCode) {
            final int nextAddress = arg2;

            arg2 = address;

            if (nextAddress < -1)
                ((Instruction) program.get(- (nextAddress + 2))).fixupFuture(address);
        } else
            throw new RuntimeException("Fixup on non-future instruction.");
    }

    public void fixupJump(int address) {
        if (opcode == jumpCode) {
            final int nextAddress = arg1;

            arg1 = address;

            if (nextAddress < -1)
                ((Instruction) program.get(- (nextAddress + 2))).fixupJump(address);
        } else
            throw new RuntimeException("Fixup on non-jump instruction.");
    }

    public String toString() {
        String s = ""+address+": ";

        switch (opcode) {
        case nopCode :      s = s+"nop      "; break;
        case futureCode :   s = s+"future   "+arg1+","+arg2+","+arg3; break;
        case callCode :     s = s+"call     "+arg1; break;
        case scheduleCode : s = s+"schedule "+arg1+","+arg2+","+(arg3 & 0xFFF)+","+(arg3>>12); break;
        case ifCode :       s = s+"if       "+arg1+","+arg2+","+arg3; break;
        case jumpCode :     s = s+"jump     "+arg1; break;
        case returnCode :   s = s+"return   "; break;
        default :                ; break;
        }

        return s + "       // " + this.comment;

    }

    /**
     * Returns the instruction at address.
     * @return Instruction
     */
    public static Instruction get(int address) {
        return (Instruction) program.get(address);
    }

    /**
     * Returns the number of instructions.
     * @return int
     */
    public static int getNumberOf() {
        if (program == null) return 0;
        return program.size();
    }

    /**
     * Returns a list iterator over all instructions.
     * @return ListIterator
     */
    public static ListIterator getListIterator() {
        return program.listIterator();
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

    /**
     * Sets the maxUnits.
     * @param maxUnits The maxUnits to set
     */
    public static void setMaxUnits(int maxUnits) {
        Instruction.maxUnits = maxUnits;
    }

    /**
     * Sets the minUnitPeriod.
     * @param minUnitPeriod The minUnitPeriod to set
     */
    public static void setMinUnitPeriod(int minUnitPeriod) {
        Instruction.minUnitPeriod = minUnitPeriod;
    }

    /**
     * Returns the taskInvocationAddresses.
     * @return Collection
     */
    public static Collection getTaskInvocationAddresses() {
        return taskInvocationAddresses.values();
    }

    /**
     * Method hasTaskInvocationAddresses.
     * @param unitModeName
     * @return boolean
     */
    public static boolean hasTaskInvocationAddresses(String unitModeName) {
        return taskInvocationAddresses.containsKey(unitModeName);
    }

    /**
     * Method getTaskInvocationAddresses.
     * @param unitModeName
     * @return FixupChain
     */
    public static FixupChain getTaskInvocationAddresses(String unitModeName) {
        return (FixupChain) taskInvocationAddresses.get(unitModeName);
    }

    /**
     * Method addTaskInvocationAddresses.
     * @param unitModeName
     * @param fixupChain
     */
    public static void addTaskInvocationAddresses(String unitModeName, FixupChain fixupChain) {
        taskInvocationAddresses.put(unitModeName, fixupChain);
    }

    /**
     * Method resetTaskInvocationAddresses.
     */
    public static void resetTaskInvocationAddresses() {
        taskInvocationAddresses = new Hashtable();
    }

    public static void reset() {
        program = new ArrayList();
    }

    public Ecode makeEcode() {
        Ecode e = new Ecode(this.address, this.opcode, this.arg1, this.arg2, this.arg3, this.comment);
        return e;
    }

}
