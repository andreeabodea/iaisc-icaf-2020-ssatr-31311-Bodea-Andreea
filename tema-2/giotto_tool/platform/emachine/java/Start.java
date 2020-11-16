package platform.emachine.java;
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

/**
 * @author M.A.A. Sanvido
 * @version Start.java,v 1.8 2004/09/29 03:34:54 cxh Exp
 * @since Giotto 1.0.1
 */

import giotto.functionality.table.TableEntry;

import platform.emachine.core.EmulatorPanel;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Start extends Frame implements ActionListener, WindowListener {


    EmulatorPanel contentPane = new EmulatorPanel();

    private Menu menuEcode;
    private MenuItem menuLoadPkg;
    private MenuBar menuBar;

    private boolean remote = false;
    private String[] remfiles;
    private MenuItem[] menuLoad;
    //file access
    FileDialog chooser;



    public Start(String[] remfiles) {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        // declares if the load has to be remote or not
        this.remote = (remfiles!=null && remfiles.length != 0);
        this.remfiles = remfiles;

        menuEcode = new Menu();
        menuLoadPkg = new MenuItem();
        menuBar = new MenuBar();

        try {
            if (!remote) chooser = new FileDialog(this);

            this.add(contentPane);
            this.addWindowListener(this);

            this.setTitle("E code Interpreter");

            menuEcode.setLabel("Menu");
            if (!this.remote) {
                menuLoadPkg.setLabel("Load E code");
                menuLoadPkg.addActionListener(this);
                menuEcode.add(menuLoadPkg);
            } else {
                menuLoad = new MenuItem[remfiles.length-1];
                for (int i = 0; i < menuLoad.length; i++) {
                    menuLoad[i] = new MenuItem("Load Ecode "+remfiles[i+1]);
                    menuLoad[i].addActionListener(this);
                    menuEcode.add(menuLoad[i]);
                }

            }

            menuBar.add(menuEcode);
            this.setMenuBar(menuBar);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void loadECode() {
        String file = chooseEcodeFile(false);
        if (file != null) {
            try {
                TableEntry.loadECode(file);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("could not load ecd!!!");
            }
        }
    }

    void loadRemoteECode(int i) {
        try {
            Emulator.LoadRemoteECD(remfiles[0] + remfiles[i+1]);
        } catch (Exception e) {
        }

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

    /**
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        Object obj = arg0.getSource();
        if (!remote && (obj == menuLoadPkg)) loadECode();
        else if (remote) {
            for (int i=0; i<menuLoad.length; i++) {
                if (obj == menuLoad[i]) {
                    loadRemoteECode(i);
                }
            }
        }
    }

    /**
     * @see java.awt.event.WindowListener#windowActivated(WindowEvent)
     */
    public void windowActivated(WindowEvent arg0) {
    }

    /**
     * @see java.awt.event.WindowListener#windowClosed(WindowEvent)
     */
    public void windowClosed(WindowEvent arg0) {
    }

    /**
     * @see java.awt.event.WindowListener#windowClosing(WindowEvent)
     */
    public void windowClosing(WindowEvent arg0) {
        this.hide();
        Emulator.Stop(null);
        System.exit(0);
    }

    /**
     * @see java.awt.event.WindowListener#windowDeactivated(WindowEvent)
     */
    public void windowDeactivated(WindowEvent arg0) {
    }

    /**
     * @see java.awt.event.WindowListener#windowDeiconified(WindowEvent)
     */
    public void windowDeiconified(WindowEvent arg0) {
    }

    /**
     * @see java.awt.event.WindowListener#windowIconified(WindowEvent)
     */
    public void windowIconified(WindowEvent arg0) {
    }

    /**
     * @see java.awt.event.WindowListener#windowOpened(WindowEvent)
     */
    public void windowOpened(WindowEvent arg0) {
    }


    /**Main method*/
    public static void main(String[] args) {
        Start frame = new Start(args);
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        frame.pack();
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

}


