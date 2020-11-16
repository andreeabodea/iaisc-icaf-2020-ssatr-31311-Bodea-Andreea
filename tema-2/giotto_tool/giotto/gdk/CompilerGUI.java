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
package giotto.gdk;

import giotto.functionality.table.Ecode;
import giotto.functionality.table.TableEntry;
import giotto.giottoc.GiottoC;
import giotto.giottoc.LogInterface;
import giotto.giottoc.StringUtilities;
import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.ListIterator;
import java.util.StringTokenizer;

/**
 * @author M.A.A. Sanvido
 * @version CompilerGUI.java,v 1.20 2004/09/29 03:34:28 cxh Exp
 * @since Giotto 1.0.1
 */
class CompilerGUI extends Frame implements LogInterface, ActionInterface {


    //        private SVGTree svgTree = new SVGTree();
    private static String EXAMPLEDIR = "examples";

    private ActionAdapter action;

    private Panel contentPane = new Panel();

    private Button runButton = new Button();
    private Button compileButton = new Button();
    private Button clearButton = new Button();

    private Label jLabel1 = new Label();

    private MenuBar menuBar = new MenuBar();

    private Menu menuHelp = new Menu();
    private MenuItem menuAbout = new MenuItem();
    private MenuItem Example1 = new MenuItem();
    private MenuItem Example2 = new MenuItem();
    private MenuItem Example3 = new MenuItem();

    private Menu menuFile = new Menu();
    private MenuItem menuExit = new MenuItem();
    private MenuItem menuOpen = new MenuItem();
    private MenuItem menuGoTo = new MenuItem();
    private MenuItem menuSave = new MenuItem();
    private MenuItem menuSaveAs = new MenuItem();
    private MenuItem menuEdit = new MenuItem();

    private Menu menuEcode = new Menu();
    private MenuItem menuRun = new MenuItem();
    private MenuItem menuShow = new MenuItem();
    private MenuItem menuSavePkg = new MenuItem();
    private MenuItem menuLoadPkg = new MenuItem();


    private TextField program_Field = new TextField();
    private TextField packages_Field = new TextField();

    private TextArea logArea = new TextArea();
    private TextArea progArea = new TextArea();

    private ScrollPane scrollLog = new ScrollPane();
    private ScrollPane scrollProg = new ScrollPane();

    private Checkbox makeJavaToggle = new Checkbox();
    private Checkbox makeCToggle = new Checkbox();
    private Checkbox annotatedToggle = new Checkbox();
    private Checkbox simulinkToggle = new Checkbox();
    private Checkbox dynamicToggle = new Checkbox();

    private FileDialog chooser = new FileDialog(this, "Giotto Program Chooser");

    /**Construct the frame*/
    public CompilerGUI() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        action = new ActionAdapter(this);
        //                menuAdapter = new MenuMouseAdapter(this);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        this.add(contentPane);
        contentPane.setSize(new Dimension(400, 400));
        contentPane.setFont(new Font("Helvetica", Font.PLAIN, 14));
        contentPane.setLayout(gridbag);

        //                jLabel1.setBounds(143, 56, 75, 17);
        jLabel1.setText("  Program:");

        //                compileButton.setBounds(9, 66, 111, 32);
        compileButton.addActionListener(action);
        compileButton.setLabel("Compile");

        //                program_Field.setBounds(143, 72, 389, 25);
        program_Field.setText(EXAMPLEDIR+File.separator+"hovercraft05.giotto");
        program_Field.addActionListener(action);
        packages_Field.setText("giotto.functionality.code.hovercraft");
        packages_Field.addActionListener(action);

        this.setMenuBar(menuBar);
        this.setSize(new Dimension(544, 364));
        this.setTitle("Giotto Compiler & Simulator");

        clearButton.setFont(new java.awt.Font("Dialog", 0, 10));
        clearButton.setLabel("Clear");
        clearButton.setBounds(462, 337 + 186 + 4, 65, 25);
        clearButton.addActionListener(action);

        //                openprgButton.setHorizontalTextPosition(SwingConstants.LEFT);
        //                openprgButton.setText("Open");
        //                openprgButton.setBounds(new java.awt.Rectangle(436, 115, 85, 25));
        //                openprgButton.addActionListener(action);
        //
        //                chooseprgButton.setHorizontalTextPosition(SwingConstants.LEFT);
        //                chooseprgButton.setText("File");
        //                chooseprgButton.setBounds(436 - 90, 115, 85, 25);
        //                chooseprgButton.addActionListener(action);

        //                ModeTree.setBounds(9, 148, 128, 186);

        menuFile.setLabel("File");
        menuExit.setLabel("Exit");
        //                menuExit.addMenuDragMouseListener(menuAdapter);
        menuExit.addActionListener(action);
        menuOpen.setLabel("Open");
        //                menuOpen.addMenuDragMouseListener(menuAdapter);
        menuOpen.addActionListener(action);
        menuGoTo.setLabel("Go To Line");
        //                menuGoTo.addMenuDragMouseListener(menuAdapter);
        menuGoTo.addActionListener(action);
        menuSave.setLabel("Save");
        //                menuSave.addMenuDragMouseListener(menuAdapter);
        menuSave.addActionListener(action);
        menuSaveAs.setLabel("Save As");
        //                menuSaveAs.addMenuDragMouseListener(menuAdapter);
        menuSaveAs.addActionListener(action);
        menuEdit.setLabel("Edit");
        if (System.getProperty("os.name").equalsIgnoreCase("windows 2000")
                || System.getProperty("os.name").equalsIgnoreCase("windows nt")
                || System.getProperty("os.name").equalsIgnoreCase("windows XP")
                || System.getProperty("os.name").equalsIgnoreCase("windows 98")
                || System.getProperty("os.name").equalsIgnoreCase("windows 95")) {
            //                        menuEdit.addMenuDragMouseListener(menuAdapter);
            menuEdit.addActionListener(action);
        } else menuEdit.setEnabled(false);

        menuEcode.setLabel("E code");
        menuRun.setLabel("Run E code");
        //                menuRun.addMenuDragMouseListener(menuAdapter);
        menuRun.addActionListener(action);
        menuShow.setLabel("Show E code");
        //                menuShow.addMenuDragMouseListener(menuAdapter);
        menuShow.addActionListener(action);
        menuSavePkg.setLabel("Save E code");
        //                menuShow.addMenuDragMouseListener(menuAdapter);
        menuSavePkg.addActionListener(action);
        menuLoadPkg.setLabel("Load E code");
        //                menuShow.addMenuDragMouseListener(menuAdapter);
        menuLoadPkg.addActionListener(action);


        menuHelp.setLabel("Help");
        menuAbout.setLabel("About");
        //                menuAbout.addMenuDragMouseListener(menuAdapter);
        menuAbout.addActionListener(action);
        Example1.setActionCommand("Elevator Example");
        Example1.setLabel("Elevator");
        //                Example1.addMenuDragMouseListener(menuAdapter);
        Example1.addActionListener(action);
        Example2.setLabel("Hovercraft");
        //                Example2.addMenuDragMouseListener(menuAdapter);
        Example2.addActionListener(action);
        Example3.setLabel("Simple");
        //                Example3.addMenuDragMouseListener(menuAdapter);
        Example3.addActionListener(action);

        logArea.setEditable(false);
        scrollLog.add(logArea);
        //                scrollLog.setVerticalScrollBarPolicy(
        //                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //                scrollLog.setBounds(9, 148 + 186 + 4, 389+144, 186);
        scrollLog.setSize(new Dimension(500, 200));
        //                scrollLog.setMinimumSize(new Dimension(400, 100));
        //                scrollLog.setMaximumSize(new Dimension(600, 300));
        //                scrollLog.getViewport().add(logArea, null);

        progArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollProg.add(progArea);
        //                scrollProg.setVerticalScrollBarPolicy(
        //                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //                scrollProg.setBounds(9, 148, 389+144, 186);
        scrollProg.setSize(new Dimension(500, 300));
        //                scrollProg.setMinimumSize(new Dimension(400, 200));
        //                scrollProg.setMaximumSize(new Dimension(600, 400));
        //                scrollProg.getViewport().add(progArea, null);

        makeJavaToggle.setLabel("make Java");
        makeJavaToggle.setState(false);
        //                noFunctionalityToggle.setToolTipText("activate/deactivate debug option");
        makeJavaToggle.setFont(new java.awt.Font("Dialog", 0, 8));
        //                debugToggle.setBounds(360, 337 + 186 + 4, 65, 25);
        makeJavaToggle.setSize(new Dimension(40, 25));
        makeJavaToggle.addMouseListener(action);

        makeCToggle.setLabel("make C");
        makeCToggle.setState(false);
        //                noFunctionalityToggle.setToolTipText("activate/deactivate debug option");
        makeCToggle.setFont(new java.awt.Font("Dialog", 0, 8));
        //                debugToggle.setBounds(360, 337 + 186 + 4, 65, 25);
        makeCToggle.setSize(new Dimension(40, 25));
        makeCToggle.addMouseListener(action);


        annotatedToggle.setLabel("annotated");
        annotatedToggle.setState(false);
        //                annotatedToggle.setToolTipText(
        //                        "activate/deactivate annotation in Giotto compiler");
        annotatedToggle.setFont(new java.awt.Font("Dialog", 0, 8));
        //                annotatedToggle.setBounds(360 - 70, 337 + 186 + 4, 65, 25);
        annotatedToggle.setSize(new Dimension(65, 25));
        annotatedToggle.addMouseListener(action);

        simulinkToggle.setLabel("simulink");
        simulinkToggle.setState(false);
        //                simulinkToggle.setToolTipText(
        //                        "activate/deactivate symulink compatible C code generation");
        simulinkToggle.setFont(new java.awt.Font("Dialog", 0, 8));
        //                simulinkToggle.setBounds(360 - 140, 337 + 186 + 4, 65, 25);
        simulinkToggle.setSize(new Dimension(65,25));
        simulinkToggle.addMouseListener(action);

        dynamicToggle.setLabel("dynamic");
        dynamicToggle.setState(false);
        //                dynamicToggle.setToolTipText(
        //                        "activate/deactivate dynaic E code generation");
        dynamicToggle.setFont(new java.awt.Font("Dialog", 0, 8));
        //                dynamicToggle.setBounds(360 - 210, 337 + 186 + 4, 65, 25);
        dynamicToggle.setSize(new Dimension(65,25));
        dynamicToggle.addMouseListener(action);

        /** panel configuration */

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0;
        c.gridheight = 2;
        contentPane.add(compileButton, c);

        c.gridheight = 1;
        contentPane.add(makeJavaToggle, c);
        contentPane.add(makeCToggle, c);
        contentPane.add(packages_Field, null);
        contentPane.add(simulinkToggle, c);
        contentPane.add(annotatedToggle, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        contentPane.add(dynamicToggle, c);

        c.gridwidth = 1;
        contentPane.add(jLabel1, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        contentPane.add(program_Field, c);
        //
        //                contentPane.add(openprgButton, null);
        //                contentPane.add(chooseprgButton, null);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        contentPane.add(scrollProg, c);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        contentPane.add(scrollLog, c);
        //                contentPane.add(ModeTree, null);
        //                contentPane.add(jLabel4, null);
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        contentPane.add(clearButton, c);


        /** menu bar configuration */
        menuBar.add(menuFile);
        menuBar.add(menuEcode);
        menuBar.add(menuHelp);
        menuFile.add(menuOpen);
        // Goto menu does not work?
        //menuFile.add(menuGoTo);
        menuFile.add(menuSave);
        menuFile.add(menuSaveAs);
        menuFile.add(menuEdit);
        menuFile.add(menuExit);
        menuEcode.add(menuRun);
        menuEcode.add(menuShow);
        menuEcode.add(menuSavePkg);
        menuEcode.add(menuLoadPkg);
        menuHelp.add(menuAbout);
        menuHelp.add(Example1);
        menuHelp.add(Example2);
        menuHelp.add(Example3);

        makeJavaToggle.setState(true);
        loadEditor(program_Field.getText());
    }

    String[] processPackages (String s) {
        StringTokenizer st = new StringTokenizer(s);
        String[] out = {"giotto.functionality.code"}, temp;
        while (st.hasMoreTokens()) {
            temp = out;
            out = new String[temp.length+1];
            for (int i = 0; i < temp.length; i++) {
                out[i] = temp[i];
            }
            out[temp.length] = st.nextToken();
        }
        return out;
    }

    void compile() {
        ListIterator list;

        if (progArea.getText().length() == 0) {
            loadEditor(program_Field.getText());
            if (progArea.getText().length() == 0) {
                return;
            }
        }

        GiottoC.setLog(this);
        GiottoC.displayCopyright();

        try {
            GiottoC.processGiotto(
                    new StringReader(progArea.getText()),
                    null,
                    annotatedToggle.getState(),
                    simulinkToggle.getState(),
                    dynamicToggle.getState(),
                    makeJavaToggle.getState(), makeCToggle.getState(),
                    processPackages(packages_Field.getText()));
        } catch (Throwable throwable) { 
            this.print(StringUtilities.stackTraceToString(throwable));
        }

        //                /** copy the Instruction to ECode */
        //                list = Instruction.getListIterator();
        //                Ecode.Init(Instruction.getNumberOf(), Instruction.getMaxUnits(), Instruction.getMinUnitPeriod());
        //                while (list.hasNext()) {
        //                        ((Instruction)list.next()).makeEcode();
        //                }
        //                svgTree.deleteNodes();
        //                ModeTree.repaint();
        //
        //                                if (JCompiler.done) {
        //                                        svgTree.defineListener(ModeTree);
        //                                        svgTree.createNodes();
        //                                        ModeTree.repaint();
        //                                }
    }

    void openRun() {
        EmachineGUI frame = new EmachineGUI();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                (d.width - frame.getSize().width) / 2,
                (d.height - frame.getSize().height) / 2);
        frame.setVisible(true);
        frame.pack();
    }

    void openShow() {
        EcodeGUI frame = new EcodeGUI();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                (d.width - frame.getSize().width) / 2,
                (d.height - frame.getSize().height) / 2);
        frame.setVisible(true);
        frame.pack();
    }

    void saveECode(String fileName) {
        if (fileName != null) {
            try {
                TableEntry.saveECode(fileName);
            } catch (Exception e) {
                println("Cannot save E Code file '" + fileName + "': "
                        + StringUtilities.stackTraceToString(e));
            }
        } else {
            println("could not save E Code, fileName was null?");
        }
    }


    void loadECode(String fileName) {
        if (fileName != null) {
            try {
                TableEntry.loadECode(fileName);
                println("Load E Code: " + fileName + ", " + Ecode.getNumberOf()
                        + " E code instructions");
            } catch (Exception e) {
                println("Could not load E Code file '" + fileName + "': "
                        + StringUtilities.stackTraceToString(e));
            }
        } else {
            println("Could not load E Code, fileName was null?");
        }
    }


    void loadEditor(String fileName) {
        File f = new java.io.File(fileName);
        BufferedReader br;
        try {
            if (f.exists() && f.isFile() && f.canRead()) {
                progArea.setText("");
                br = new BufferedReader(new FileReader(f));
                while (br.ready()) {
                    progArea.append(br.readLine());
                    progArea.append("\n");
                }
                // Set the location in the ProgArea to the top of the file.
                try {
                    progArea.setCaretPosition(0);
                } catch (java.awt.IllegalComponentStateException ex) {
                    // Ignore

                    // When the constructor is called, the first file
                    // gets loaded but the peer is not present, so we
                    // might get:

                    // "Cannot set caret position until after the peer
                    // has been created"

                }
            } else {
                println("cannot open file '" + fileName + "', it either "
                        + "does not exist, is not a file or is "
                        + "not readable."); 
            }
        } catch (Exception e) {
            println("Cannot load file '" + fileName + "': " 
                    + StringUtilities.stackTraceToString(e));
        }
    }

    void goToPos(int pos) {
        //                progArea.grabFocus();
        progArea.setCaretPosition(pos);

    }


    void saveEditor(String fileName) {
        File f = new java.io.File(fileName);
        BufferedWriter br;
        try {
            if ((!f.exists()) || (f.isFile() && f.canWrite())) {
                br = new BufferedWriter(new FileWriter(f));
                br.write(progArea.getText());
                br.flush();
                println("saved");
            } else
                println("Cannot save file '" + fileName + "', if it exists, "
                        + "it might not be writable.");
        } catch (Exception e) {
            println("Cannot save file '" + fileName + "': " 
                    + StringUtilities.stackTraceToString(e));
        }
    }


    void launchExternalEditor() {
        try {
            // FIXME: this only works under Windows?
            Runtime.getRuntime().exec("notepad " + program_Field.getText());
        } catch (Exception e) {
            println("Cannot edit file '" + program_Field.getText() + "': " 
                    + StringUtilities.stackTraceToString(e));
        }
    }

    void openPosChooser() {
        //Position pos = new Position();
        //pos.Open(logArea.getSelectedText());
        //while (pos.isOpen()) {};
        //if (pos.Line() > 0) goToPos(pos.Line());
        goToPos(Integer.parseInt(logArea.getSelectedText()));
    }

    void openAbout() {
        new About(
                "About Giotto",
                "Giotto 1.0.1\nCopyright (c) 2001-2004 The Regents of the University of California, All Rights Reserved.\nGiotto was created by C.M. Kirsch, M.A.A Sanvido, T.A. Henzinger\nFor more information, see http://www-cad.eecs.berkeley.edu/~giotto\nor email giotto@www-cad.eecs.berkeley.edu");
    }

    boolean chooseFile(boolean save) {
        String file;
        /** set chooser to current directory */
        chooser.setDirectory(System.getProperty("user.dir"));
        if (save) {
            chooser.setMode(FileDialog.SAVE);
        } else {
            chooser.setMode(FileDialog.LOAD);
        }
        chooser.show();
        file = chooser.getFile();
        if (file != null) {
            program_Field.setText(chooser.getDirectory()+file);
        }
        return file != null;
    }

    String chooseEcodeFile(boolean save) {
        /** set chooser to current directory */
        chooser.setDirectory(System.getProperty("user.dir"));
        if (save) {
            chooser.setMode(FileDialog.SAVE);
        } else {
            chooser.setMode(FileDialog.LOAD);
        }
        chooser.show();
        return chooser.getDirectory()+chooser.getFile();
    }


    // ------------------- LogInterface Implementation //
    /**
     * @see gdk.LogInterface#println()
     */
    public void println() {
        logArea.append("\n");
        try {
            logArea.setCaretPosition(Integer.MAX_VALUE);
        } catch (java.awt.IllegalComponentStateException ex) {
            // Ignore
            // When the constructor is called, the first file gets loaded
            // but the peer is not present, so we might get:
            // "Cannot set caret position until after the peer has been created"
        }
    }

    /**
     * @see gdk.LogInterface#println(String)
     */
    public void println(String s) {
        logArea.append(s);
        println();
    }

    /**
     * @see gdk.LogInterface#println(int)
     */
    public void println(int i) {
        logArea.append(Integer.toString(i));
        println();
    }

    /**
     * @see gdk.LogInterface#print(String)
     */
    public void print(String s) {
        logArea.append(s);
        logArea.setCaretPosition(Integer.MAX_VALUE);
    }

    /**
     * @see gdk.LogInterface#print(int)
     */
    public void print(int i) {
        logArea.append(Integer.toString(i));
        logArea.setCaretPosition(Integer.MAX_VALUE);
    }

    public void clear() {
        logArea.setText("");
    }

    // ------------------- User Interface Implemantation //

    /** general action handler */
    public void actionHandler(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == menuAbout) openAbout();
        if (obj == menuExit) System.exit(0);
        if (obj == Example3) {
            program_Field.setText(EXAMPLEDIR+File.separator+"java_simple.giotto");
            packages_Field.setText("giotto.functionality.code.simple");
            loadEditor(EXAMPLEDIR+File.separator+"java_simple.giotto");
        }
        if (obj == Example2) {
            program_Field.setText(EXAMPLEDIR+File.separator+"hovercraft05.giotto");
            packages_Field.setText("giotto.functionality.code.hovercraft");
            makeCToggle.setState(false);
            makeJavaToggle.setState(true);
            loadEditor(EXAMPLEDIR+File.separator+"hovercraft05.giotto");
        }
        if (obj == Example1) {
            program_Field.setText(EXAMPLEDIR+File.separator+"elevator.giotto");
            packages_Field.setText("giotto.functionality.code.elevator");
            loadEditor(EXAMPLEDIR+File.separator+"elevator.giotto");
        }
        if ((obj == menuOpen) && chooseFile(false)) loadEditor(program_Field.getText());
        if (obj == menuGoTo) openPosChooser();
        if (obj == menuSave) saveEditor(program_Field.getText());
        if ((obj == menuSaveAs) && chooseFile(true)) saveEditor(program_Field.getText());
        if (obj == menuEdit) launchExternalEditor();
        if (obj == menuRun) openRun();
        if (obj == menuShow) openShow();
        if (obj == menuSavePkg) saveECode(chooseEcodeFile(true));
        if (obj == menuLoadPkg) loadECode(chooseEcodeFile(false));
        if (obj == compileButton) compile();
        if (obj == clearButton) clear();
        if (obj == program_Field) loadEditor(program_Field.getText());

    }

    public void mouseHandler (MouseEvent e) {
        Object obj = e.getSource();
        if (obj == makeJavaToggle) {}
        if (obj == makeCToggle) {}
        if (obj == simulinkToggle) {}
        if (obj == dynamicToggle) {}
        if (obj == annotatedToggle) {}
    }

    /**Overridden so we can exit when window is closed*/
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }



    //        void menuHandler(MouseEvent e) {
    //                Object obj = e.getSource();
    //                if (obj == menuAbout)
    //                        openAbout();
    //                if (obj == menuExit)
    //                        System.exit(0);
    //                if (obj == Example3)
    //                        program_Field.setText(EXAMPLEDIR+File.separator+"java_simple.giotto");
    //                if (obj == Example2)
    //                        program_Field.setText(EXAMPLEDIR+File.separator+"hovercraft.giotto");
    //                if (obj == Example1)
    //                        program_Field.setText(EXAMPLEDIR+File.separator+"elevator.giotto");
    //                if (obj == menuOpen) {
    //                        if (chooseFile(false))
    //                                loadEditor(program_Field.getText());
    //                }
    //                if (obj == menuGoTo) {
    //                        openPosChooser();
    //                }
    //                if (obj == menuSave) saveEditor(program_Field.getText());
    //                if (obj == menuSaveAs)
    //                        if (chooseFile(true)) saveEditor(program_Field.getText());
    //                if (obj == menuEdit) launchExternalEditor();
    //                if (obj == menuRun)
    //                        openRun();
    //                if (obj == menuShow)
    //                        openShow();
    //
    //        }


}






