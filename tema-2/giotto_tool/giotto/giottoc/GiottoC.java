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

import giotto.functionality.table.Condition;
import giotto.functionality.table.Driver;
import giotto.functionality.table.Ecode;
import giotto.functionality.table.Host;
import giotto.functionality.table.Port;
import giotto.functionality.table.TableEntry;
import giotto.functionality.table.Task;
import giotto.functionality.table.Trigger;
import giotto.giottoc.ecode.Instruction;
import giotto.giottoc.lexer.Lexer;
import giotto.giottoc.node.Start;
import giotto.giottoc.parser.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ListIterator;
import java.util.Vector;

/**

This class contains the main method for giottoc.  Some code fragments
are protected by the copyright of the SableCC team.

@author Christoph Kirsch
@version GiottoC.java,v 1.19 2004/09/29 03:52:06 cxh Exp
@since Giotto 1.0.1
*/
public class GiottoC {

    private static String OUTPUTDIR = "c_output";
    private static String OUTECDNAME = "out.ecd";
    private static String[] func_packages = {"giotto.functionality.code"};
    private static boolean makeJava = false;
    private static boolean makeC = false;
    private static boolean pureGiotto = true;
    private static boolean dynamicGiotto = false;
    private static boolean simulinkGiotto = false;
    private static LogInterface log = new SystemLog();

    public static void displayCopyright() {
        log.println();
        log.println("This is the Giotto compiler giottoc version 0.4");
        log.println();
        log.println("Copyright (C) 2001-2004 The Regents of the University of California.");
        log.println("All rights reserved.");
        log.println();
        log.println("See the copyright.txt file for details.");
        log.println();
        log.println("This software comes with ABSOLUTELY NO WARRANTY.");
        log.println();
    }

    private static void displayUsage() {
        log.println("Usage: giottoc [-d destination | -annotated | -dynamic | -java | -out name | -fp name{,name} | -C | -simulink] filename");
    }

    public static void setLog(LogInterface l) {
        if (l != null) log = l;
    }

    public static LogInterface getLog() {
        return log;
    }

    public static void main(String[] arguments) {
        String d_option = null;
        Vector filename = new Vector();
        log = new SystemLog();
        File in;
        File dir;

        if (arguments.length == 0) {
            displayCopyright();
            displayUsage();
            System.exit(1);
        }

        displayCopyright();

        {
            int arg = 0;

            String[] temp_packages;

            while (arg < arguments.length) {
                if (arguments[arg].equals("-d")) {
                    if ((d_option == null) && (++arg < arguments.length)) {
                        d_option = arguments[arg];
                    } else {
                        displayUsage();
                        System.exit(1);
                    }
                } else if (arguments[arg].equals("-annotated")) {
                    if (pureGiotto) {
                        pureGiotto = false;
                    } else {
                        displayUsage();
                        System.exit(1);
                    }
                } else if (arguments[arg].equals("-dynamic")) {
                    if (!dynamicGiotto) {
                        dynamicGiotto = true;
                    } else {
                        displayUsage();
                        System.exit(1);
                    }
                } else if (arguments[arg].equals("-simulink")) {
                    if (!simulinkGiotto) {
                        simulinkGiotto = true;
                    } else {
                        displayUsage();
                        System.exit(1);
                    }
                } else if (arguments[arg].equals("-java")) {
                    if (!makeJava) {
                        makeJava = true;
                    } else {
                        displayUsage();
                        System.exit(1);
                    }
                } else if (arguments[arg].equals("-out") && (++arg < arguments.length)) {
                    OUTECDNAME = arguments[arg];
                } else if (arguments[arg].equals("-fp") && (++arg < arguments.length)) {
                    temp_packages = func_packages;
                    func_packages = new String[temp_packages.length+1];
                    for (int i=0; i < temp_packages.length; i++) {func_packages[i] = temp_packages[i];}
                    func_packages[temp_packages.length] = arguments[arg];
                } else if (arguments[arg].equals("-c")) {
                    if (!makeC) {
                        makeC = true;
                    } else {
                        displayUsage();
                        System.exit(1);
                    }
                } else if (filename.size() == 0) {
                    filename.addElement(arguments[arg]);
                } else {
                    displayUsage();
                    System.exit(1);
                }

                arg++;
            }

            if (filename.size() == 0) {
                displayUsage();
                System.exit(1);
            }

        }

        try {
            for (int i = 0; i < filename.size(); i++) {
                in = new File((String) filename.elementAt(i));
                in = new File(in.getAbsolutePath());
                if (d_option == null) {
                    dir = new File(in.getParent());
                } else {
                    dir = new File(d_option);
                    dir = new File(dir.getAbsolutePath());
                }

                if (!dir.exists()) {
                    log.println("ERROR: destination directory " + dir.getName() + " does not exist.");
                    System.exit(1);
                }
                if (!in.exists()) {
                    log.println("ERROR: Giotto program file " + in.getName() + " does not exist.");
                    System.exit(1);
                }
                processGiotto(new FileReader(in), dir);
            }
        } catch (Throwable throwable) {
            log.println(StringUtilities.stackTraceToString(throwable));
            System.err.println(StringUtilities.stackTraceToString(throwable));
            System.exit(1);
        } finally {
            System.exit(0);
        }
    }


    public static void processGiotto(Reader in, File dir, boolean ann, boolean sim, boolean dyn, boolean java, boolean C, String[] pcks) throws Exception, Throwable {
        dynamicGiotto = dyn;
        simulinkGiotto = sim;
        pureGiotto = !ann;
        makeJava = java;
        makeC = C;
        func_packages = pcks;
        processGiotto(in, dir);
    }

    private static void processGiotto(Reader in, File dir)
            throws Exception, Throwable {
        ListIterator list;

        /** if no output dir defined set to default */
        if (dir == null) {
            dir = new File(System.getProperty("user.dir")+File.separator+ OUTPUTDIR);
        }

        if (makeJava)
            log.println("-- generating Ecode for Java in " + OUTECDNAME);

        if (makeC)
            log.println("-- generating Ecode with C stubs in "+ dir.getPath());

        if (!pureGiotto)
            log.println("-- Analyzing annotations");

        if (dynamicGiotto)
            log.println("-- Dynamic Giotto (Prototype)");

        if (simulinkGiotto)
            log.println("-- Generate Simulink-compatible code (Prototype)");

        log.println();


        // Build the AST
        final Start tree = new Parser(new Lexer(new PushbackReader(in))).parse();

        in.close();

        try {
            SymbolTable symbolTable;
            FTable functionTable;
            STable scheduleTable;
            HTable hostTable;
            NTable networkTable;

            // Reset tables
            Task.reset();
            Driver.reset();
            Condition.reset();
            Trigger.reset();
            Port.reset();
            Instruction.reset();
            Ecode.reset();
            Host.reset();

            TableEntry.enableClassLoader(makeJava, func_packages);

            // Apply concrete to abstract syntax tree translations
            tree.apply(new CST2AST());

            // Get symbol table
            tree.apply(symbolTable = new SymbolTable(dir));

            // Type check
            tree.apply(new TypeChecker(symbolTable, dynamicGiotto));

            // Frequency check
            tree.apply(new FrequencyChecker(symbolTable, dynamicGiotto));

            // Time safety check
            tree.apply(new TimeSafetyChecker(symbolTable, pureGiotto));



            // Generate function table
            tree.apply(functionTable = new FTable(symbolTable, pureGiotto, dynamicGiotto, simulinkGiotto));
            // Create schedule table
            scheduleTable = new STable(symbolTable);
            // Generate host table
            tree.apply(hostTable = new HTable(symbolTable, pureGiotto));
            // Generate network table
            tree.apply(networkTable = new NTable(symbolTable, pureGiotto));

            // Generate E code
            tree.apply(new ECode(symbolTable, functionTable, scheduleTable, hostTable, pureGiotto, dynamicGiotto));



            if (makeJava) {
                /** copy the Instruction to ECode */
                list = Instruction.getListIterator();
                Ecode.Init(Instruction.getNumberOf(), Instruction.getMaxUnits(), Instruction.getMinUnitPeriod());
                while (list.hasNext()) {
                    ((Instruction)list.next()).makeEcode();
                }
                try {
                    TableEntry.saveECode(OUTECDNAME);
                } catch (Exception e) {
                    log.println("not able to store in " + OUTECDNAME + " error: " + e.toString());
                }
                TableEntry.Dump();
            }

            if (makeC) {
                // Emit function table
                (new FGen(symbolTable, simulinkGiotto)).emitFiles();

                // Emit oil file for OSEKWorks
                (new OilGen(symbolTable)).emitFiles();

                // Emit schedule table
                (new SGen(symbolTable)).emitFiles();

                // Emit host table
                (new HGen(symbolTable, pureGiotto)).emitFiles();

                // Emit network table
                (new NGen(symbolTable, pureGiotto)).emitFiles();

                // Emit E code
                (new EGen(symbolTable)).emitFiles();
            }


            log.println("Done.");
        } catch (Throwable throwable) {
            log.println(StringUtilities.stackTraceToString(throwable));
        }
    }


}
