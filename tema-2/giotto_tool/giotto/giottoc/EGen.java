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

package giotto.giottoc;

import giotto.giottoc.ecode.FixupChain;
import giotto.giottoc.ecode.Instruction;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author Christoph Kirsch
 * @version EGen.java,v 1.10 2004/09/29 03:51:58 cxh Exp
 * @since Giotto 1.0.1
 */
public class EGen extends CodeGen {
    public EGen(SymbolTable symbolTable) {
        super(symbolTable, "ecode", "e_code.c", "e_code.h", "e_spec.txt", "e_code.b");
    }

    public void emitCFileHeader() {
        emit("Header");

        emit("Include");
    }

    private void emitCode(Instruction instruction) {
        final String address = Integer.toString(instruction.getAddress());

        final int opcode = instruction.getOpcode();

        final String arg1 = Integer.toString(instruction.getArg1());
        final String arg2 = Integer.toString(instruction.getArg2());
        final String arg3 = Integer.toString(instruction.getArg3());

        final String comment = instruction.getComment();

        emit("Comment", new String[] { address });

        switch (opcode) {
        case Instruction.nopCode :
            emit("NOP");
            break;
        case Instruction.futureCode :
            emit("FUTURE", new String[] { arg1, arg2, arg3, comment });
            break;
        case Instruction.callCode :
            emit("CALL", new String[] { arg1, comment });
            break;
        case Instruction.scheduleCode :
            emit("SCHEDULE", new String[] { arg1, arg2, arg3, comment });
            break;
        case Instruction.ifCode :
            emit("IF", new String[] { arg1, arg2, arg3, comment });
            break;
        case Instruction.jumpCode :
            emit("JUMP", new String[] { arg1, comment });
            break;
        case Instruction.returnCode :
            emit("RETURN", new String[] { comment });
            break;
        }
    }

    public void emitCFileBody() {
        emit("ProgramHeader");

        int address = 0;

        for (ListIterator iterator = Instruction.getListIterator(); iterator.hasNext(); address++) {
            Instruction instruction = (Instruction) iterator.next();

            if (address != 0)
                emit("ProgramComma");

            emitCode(instruction);
        }

        emit("ProgramEnd");
    }

    public void emitHFileHeader() {
        emit("Header");
    }

    public void emitHFileBody() {
        emit(
                "ProgramSize",
                new String[] {
                    Integer.toString(Instruction.getNumberOf()),
                    "300",
                    Integer.toString(Instruction.getMaxUnits()),
                    Integer.toString(Instruction.getMinUnitPeriod()) });
    }

    public void emitBFile() {
        int modeCount = 0;

        Collection collection = Instruction.getTaskInvocationAddresses();

        for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            FixupChain fixupChain = (FixupChain) iterator.next();

            if (fixupChain.getUnit() == 0)
                modeCount++;
        }

        emitBinaryCode(modeCount);

        for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            FixupChain fixupChain = (FixupChain) iterator.next();

            if (fixupChain.getUnit() == 0)
                if (fixupChain.getAddress() >= 0) {
                    emitBinaryName(fixupChain.getModeName());
                    emitBinaryCode(fixupChain.getAddress());
                }
        }

        for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            FixupChain fixupChain = (FixupChain) iterator.next();

            if (fixupChain.getUnit() == 0)
                if (fixupChain.getAddress() < 0) {
                    emitBinaryName(fixupChain.getModeName());
                    emitBinaryCode(fixupChain.getAddress());
                }
        }

        emitBinaryCode(Instruction.getNumberOf());
        emitBinaryCode(Instruction.getMinUnitPeriod());

        for (ListIterator iterator = Instruction.getListIterator(); iterator.hasNext();) {
            Instruction instruction = (Instruction) iterator.next();

            emitBinaryCode(instruction.getOpcode());
            emitBinaryCode(instruction.getArg1());
            emitBinaryCode(instruction.getArg2());
            emitBinaryCode(instruction.getArg3());
        }
    }

}
