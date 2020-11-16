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

/**
 * This class implements code generation of the scheduling annotations.
 * A scheduling annotation defines the scheduling semantics of tasks.
 * The target machine will invoke and schedule tasks according to the
 * scheduling annotations.
 *
 * @author Christoph Kirsch
 * @version SGen.java,v 1.7 2004/09/29 03:52:22 cxh Exp
 * @since Giotto 1.0.1
 */
public class SGen extends CodeGen {
    public SGen(SymbolTable symbolTable) {
        super(symbolTable, "stable", "s_table.c", "s_table.h", "s_spec.txt");
    }

    public void emitCFileHeader() {
        emit("Header");

        emit("Include");
    }

    public void emitCFileBody() {
        emit("AnnotationTableHeader");

        emit(
                "AnnotationTableElement",
                new String[] {
                    "giotto_annotation",
                    "giotto_schedule_release_code",
                    "giotto_schedule_save_release_time_code",
                    "giotto_schedule_start_time_code",
                    "giotto_schedule_edf_priority" });

        emit("TableEnd");
    }

    public void emitHFileHeader() {
        emit("Header");
    }

    public void emitHFileBody() {
        final int nAnnotations = 1;

        emit("TableSize", new String[] { Integer.toString(nAnnotations)});
    }

}
