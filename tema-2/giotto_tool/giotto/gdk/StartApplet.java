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

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Applet Emulator. Visual Emulator for Giotto.
 * @author M.A.A. Sanvido
 * @version StartApplet.java,v 1.11 2004/09/29 03:31:09 cxh Exp
 * @since Giotto 1.0.1
 */
public class StartApplet extends Applet implements ActionListener {

    CompilerGUI compiler;

    //Get a parameter value
    public String getParameter(String key, String def) {
        return getParameter(key);
    }

    //Construct the applet
    public StartApplet() {
    }
    //Initialize the applet
    public void init() {
        Button button = new Button("Start Compiler");
        button.addActionListener(this);
        add(button, BorderLayout.CENTER);
    }

    //Start the applet
    public void start() {
    }
    //Stop the applet
    public void stop() {
    }
    //Destroy the applet
    public void destroy() {
    }
    //Get Applet information
    public String getAppletInfo() {
        return "Giotto Compiler Applet";
    }
    //Get parameter info
    public String[][] getParameterInfo() {
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        compiler = new CompilerGUI();
    }
}
