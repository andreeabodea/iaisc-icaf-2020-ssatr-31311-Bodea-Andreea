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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;

/**
 * @author M.A.A. Sanvido
 * @version About.java,v 1.12 2004/09/29 03:40:41 cxh Exp
 * @since Giotto 1.0.1
 */
class About extends JDialog implements ActionInterface {
    JButton jButton1 = new JButton();
    Component component1;
    JTextPane jTextPane1 = new JTextPane();

    public About(String s, String text) {

        component1 = Box.createHorizontalStrut(8);
        jButton1.setText("Ok");
        jButton1.setBounds(new java.awt.Rectangle(178, 175, 79, 27));
        jButton1.addActionListener(new ActionAdapter(this));
        this.getContentPane().setLayout(null);
        component1.setBounds(new java.awt.Rectangle(167, 18, 8, 0));
        jTextPane1.setToolTipText("");
        //jTextPane1.setBorder(BorderFactory.createEmptyBorder().createRaisedBevelBorder());
        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Dialog", 1, 12));
        jTextPane1.setBackground(SystemColor.control);
        jTextPane1.setBounds(new java.awt.Rectangle(25, 16, 396, 125));
        jTextPane1.setText(text);


        this.getContentPane().add(component1);
        this.getContentPane().add(jTextPane1);
        this.getContentPane().add(jButton1);

        this.setResizable(false);
        this.setTitle(s);
        this.setSize(450, 290);

        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        this.setVisible(true);
    }

    public void actionHandler(ActionEvent e) {
        this.hide();
    }

    public void mouseHandler(MouseEvent e) {
    }

}

