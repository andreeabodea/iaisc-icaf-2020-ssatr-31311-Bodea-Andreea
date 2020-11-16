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
import giotto.giottoc.ecode.Instruction;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author M.A.A. Sanvido
 * @version EcodeGUI.java,v 1.15 2004/09/29 03:34:30 cxh Exp
 * @since Giotto 1.0.1
 */
class EcodeGUI extends Frame implements WindowListener {

    Ecode ecode;

    TextArea text = new TextArea();
    //        JScrollPane scroll= new JScrollPane();



    public EcodeGUI() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        text.setFont(new Font("Monospaced", Font.PLAIN, 12));
        try {
            //                        Panel contentPane = new Panel();
            //                        this.add(contentPane);
            //

            text.setSize(new Dimension(400, 450));
            //                        contentPane.setLayout(gridbag);
            //                        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            //                        scroll.setPreferredSize(new Dimension(500, 200));
            //                        scroll.setMinimumSize(new Dimension(400, 100));
            //                        scroll.setMaximumSize(new Dimension(600, 300));
            //                        scroll.getViewport().add(text, null);


            //                        contentPane.add(scroll, null);
            this.add(text);
            this.setTitle("Generated E code");
            this.setSize(text.getSize());
            this.addWindowListener(this);
            fillEcode(text);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void fillEcode(TextArea text) {
        int i = 0;
        if (Ecode.getNumberOf() > 0) {
            while (i < Ecode.getNumberOf()) {
                text.append(Ecode.get(i).toString()+"\n");
                i++;
            }
        } else {
            while (i < Instruction.getNumberOf()) {
                text.append(Instruction.get(i).toString()+"\n");
                i++;
            }
        }

        text.append(TableEntry.DumpAll());
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

}


