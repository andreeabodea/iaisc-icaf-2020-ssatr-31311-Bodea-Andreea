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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**

This abstract class implements some basic functionality for code generation.

@author Christoph Kirsch
@version CodeGen.java,v 1.9 2004/09/29 03:51:54 cxh Exp
@since Giotto 1.0.1
*/
public abstract class CodeGen {
    protected SymbolTable symbolTable;

    protected String pkgName;
    protected String fileNameC;
    protected String fileNameH = null;
    protected String fileNameB = null;

    protected MacroExpander macros;

    protected File pkgDir;
    protected BufferedWriter file;
    protected String fileName = null;

    public CodeGen(SymbolTable symbolTable, String pkgName, String fileNameC, String templateFileName) {
        this.symbolTable = symbolTable;

        this.pkgName = pkgName;
        this.fileNameC = fileNameC;

        getMacros(templateFileName);
    }

    public CodeGen(
            SymbolTable symbolTable,
            String pkgName,
            String fileNameC,
            String fileNameH,
            String templateFileName) {
        this.symbolTable = symbolTable;

        this.pkgName = pkgName;
        this.fileNameC = fileNameC;
        this.fileNameH = fileNameH;

        getMacros(templateFileName);
    }

    public CodeGen(
            SymbolTable symbolTable,
            String pkgName,
            String fileNameC,
            String fileNameH,
            String templateFileName,
            String fileNameB) {
        this.symbolTable = symbolTable;

        this.pkgName = pkgName;
        this.fileNameC = fileNameC;
        this.fileNameH = fileNameH;

        this.fileNameB = fileNameB;

        getMacros(templateFileName);
    }

    private void getMacros(String templateFileName) {
        try {
            InputStream templateFileStream = 
                getClass().getResourceAsStream(templateFileName);
            if (templateFileStream == null) {
                throw new RuntimeException("Unable to find '"
                        + templateFileName + "' as a resource. "
                        + "Perhaps it is not in the classpath?");
            }
            macros =
                new MacroExpander(new InputStreamReader(templateFileStream));
        } catch (Exception ex) {
            throw new RuntimeException("Unable to open '" + templateFileName
                    + "'.", ex);
        }

        pkgDir = new File(symbolTable.pkgDir, pkgName);

        if (!pkgDir.exists()) {
            if (!pkgDir.mkdir()) {
                throw new RuntimeException("Unable to create " + pkgDir.getAbsolutePath());
            }
        }
    }

    private void openFile() {
        try {
            file = new BufferedWriter(new FileWriter(new File(pkgDir, fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create " + new File(pkgDir, fileName).getAbsolutePath());
        }
    }

    private void closeFile() {
        try {
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(
                    "An error occured while closing " + new File(pkgDir, fileName).getAbsolutePath());
        }
    }

    final public void emitFiles() {
        emitCFile();
    }

    final public void emitCFile() {
        fileName = fileNameC;

        openFile();

        emitCFileHeader();

        emitCFileBody();

        closeFile();

        if (fileNameH != null) {
            fileName = fileNameH;

            openFile();

            emitHFileHeader();

            emitHFileBody();

            closeFile();
        }

        if (fileNameB != null) {
            fileName = fileNameB;

            openFile();

            emitBFile();

            closeFile();
        }

        fileName = null;
    }

    abstract public void emitCFileHeader();
    abstract public void emitCFileBody();

    public void emitHFileHeader() {}
    public void emitHFileBody() {}

    public void emitBFile() {}

    final protected void emit(String string) {
        try {
            macros.apply(file, string);
        } catch (IOException e) {
            throw new RuntimeException(
                    "An error occured while writing to " + new File(pkgDir, fileName).getAbsolutePath());
        }
    }

    final protected void emit(String string, String[] array) {
        try {
            macros.apply(file, string, array);
        } catch (IOException e) {
            throw new RuntimeException(
                    "An error occured while writing to " + new File(pkgDir, fileName).getAbsolutePath());
        }
    }

    final protected void emitBinaryCode(int number) {
        try {
            file.write(number & 0xFF);
            file.write((number >> 8) & 0xFF);
            file.write((number >> 16) & 0xFF);
            file.write((number >> 24) & 0xFF);
        } catch (IOException e) {
            throw new RuntimeException(
                    "An error occured while writing to " + new File(pkgDir, fileName).getAbsolutePath());
        }
    }

    final protected void emitBinaryName(String name) {
        try {
            final int nameLength = name.length();

            file.write(name, 0, nameLength);

            for (int i = nameLength; i < 32; i++)
                file.write(0);
        } catch (IOException e) {
            throw new RuntimeException(
                    "An error occured while writing to " + new File(pkgDir, fileName).getAbsolutePath());
        }
    }

}
