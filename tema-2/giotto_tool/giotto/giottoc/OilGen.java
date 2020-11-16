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

import giotto.functionality.table.Task;

/**
 * This class implements code generation of the oil file for OSEKWorks.
 *
 * @author Christoph Kirsch
 * @version OilGen.java,v 1.9 2004/09/29 03:52:20 cxh Exp
 * @since Giotto 1.0.1
 */
public class OilGen extends CodeGen {
    public OilGen(SymbolTable symbolTable) {
        super(symbolTable, "oil", "control.oil", "oil_spec.txt");
    }

    public void emitCFileHeader() {
        emit("Header");

        emit("Include");
    }

    public void emitCFileBody() {
        emit("TaskHeader");

        for (int iterator = 0; iterator < Task.getNumberOf(); iterator++) {
            final Task task = Task.getTask(iterator);

            emit("Task", new String[] { task.getWrapperName() });
        }

        emit("End");
    }

}
