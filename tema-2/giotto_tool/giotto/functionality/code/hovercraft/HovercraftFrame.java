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
package giotto.functionality.code.hovercraft;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;

/**
 * @author M.A.A. Sanvido
 * @version HovercraftFrame.java,v 1.12 2004/10/01 01:09:47 cxh Exp
 * @since Giotto 1.0.1
 */
public class HovercraftFrame extends Frame implements ActionListener, MouseListener, WindowListener, Serializable {
    Panel contentPanel = new Panel();
    Label jLabel1 = new Label();
    Label jLabel2 = new Label();
    Label jLabel3 = new Label();
    Label jLabel4 = new Label();

    TextField pos = new TextField();
    TextField trgx = new TextField();
    TextField trgy = new TextField();
    TextField trga = new TextField();
    TextField lmot = new TextField();
    TextField rmot = new TextField();
    TextField errs = new TextField();

    Label jLabelInstructions = new Label();

    Map map = new Map();

    public HovercraftFrame() {

        this.add(contentPanel);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.lightGray);
        this.setTitle("The Hovercraft");

        jLabel1.setText("Hovercraft Position (X, Y, Angle): ");
        jLabel1.setBounds(20, 10, 200, 20);
        jLabel4.setText("Error (X, Y, Angle): ");
        jLabel4.setBounds(20, 32, 200, 20);
        jLabel2.setText("Target Position (X, Y, Angle): ");
        jLabel2.setBounds(20, 54, 200, 20);
        jLabel3.setText("Motors (Right, Left): ");
        jLabel3.setBounds(20, 76, 200, 20);

        pos.setBounds(220, 10, 120, 20);
        pos.setEnabled(false);
        errs.setBounds(220, 32, 120, 20);
        errs.setEnabled(false);
        trgx.setBounds(220, 54, 59, 20);
        trgy.setBounds(280, 54, 59, 20);
        trga.setBounds(340, 54, 59, 20);

        lmot.setBounds(220, 76, 59, 20);
        lmot.setEnabled(false);
        rmot.setBounds(280, 76, 59, 20);
        rmot.setEnabled(false);

        jLabelInstructions.setText("To target the hovercraft, "
                + "left mouse on the white region below:");
        jLabelInstructions.setBounds(20, 102, 360, 20);

        map.setBounds(10, 122, 400, 400);
        map.setBackground(Color.white);
        map.addMouseListener(this);


        this.contentPanel.add(jLabel1, null);
        this.contentPanel.add(jLabel2, null);
        this.contentPanel.add(jLabel3, null);
        this.contentPanel.add(jLabel4, null);
        this.contentPanel.add(pos, null);
        this.contentPanel.add(trgx, null);
        this.contentPanel.add(trgy, null);
        this.contentPanel.add(trga, null);
        this.contentPanel.add(errs, null);
        this.contentPanel.add(lmot, null);
        this.contentPanel.add(rmot, null);
        this.contentPanel.add(jLabelInstructions, null);
        this.contentPanel.add(map, null);
        lmot.addActionListener(this);
        rmot.addActionListener(this);
        trgx.addActionListener(this);
        trgy.addActionListener(this);
        trga.addActionListener(this);
        this.addWindowListener(this);
    }

    public void openWindow() {
        this.validate();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500, 520);
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        this.setVisible(true);

        Hovercraft.init(this);
    }

    /**
     * @see giotto.gdk.ActionInterface#actionHandler(ActionEvent)
     */

    public void update() {
        int x = Math.round(Hovercraft.main.X);
        int y = Math.round(Hovercraft.main.Y);
        double a = Hovercraft.main.A;

        int tx = Math.round(Hovercraft.main.TX);
        int ty = Math.round(Hovercraft.main.TY);
        int ex = Math.round(Hovercraft.main.EX);
        int ey = Math.round(Hovercraft.main.EY);
        float ea = (Math.round(Hovercraft.main.EA*100)/100.0f);
        float ea2 = (Math.round(Hovercraft.main.EA2*100)/100.0f);

        float lm = Hovercraft.main.lm;
        float rm = Hovercraft.main.rm;

        pos.setText(x +"," + y + "," + Math.round(a*100)/100.0);
        lmot.setText(Float.toString(lm));
        rmot.setText(Float.toString(rm));
        errs.setText(ex+","+ey+","+ea+","+ea2);
        map.repaint();
    }

    public void actionHandler(ActionEvent e) {
        Object obj = e.getSource();
        if ((obj == lmot) || (obj == rmot)) {
            //                                Hovercraft.main.lm = Float.parseFloat(((JTextField)lmot).getText());
            //                                Hovercraft.main.rm = Float.parseFloat(((JTextField)rmot).getText());
        }
        if ((obj == trgx) || (obj == trgy) || (obj == trga) ) {
            Hovercraft.main.TX = Float.parseFloat(((TextField)trgx).getText());
            Hovercraft.main.TY = Float.parseFloat(((TextField)trgy).getText());
            Hovercraft.main.TA = Float.parseFloat(((TextField)trga).getText());
        }
    }

    /**
     * @see giotto.gdk.ActionInterface#mouseHandler(MouseEvent)
     */
    public void mouseHandler(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            Hovercraft.main.TX = e.getPoint().x;
            Hovercraft.main.TY = e.getPoint().y;
            trgx.setText(""+Hovercraft.main.TX);
            trgy.setText(""+Hovercraft.main.TY);
            trga.setText(""+Hovercraft.main.TA);
        }
    }

    //        /**
    //         * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
    //         */
    //        public void mouseClicked(MouseEvent arg0) {
    //                Hovercraft.main.TX = arg0.getPoint().x;
    //                Hovercraft.main.TY = arg0.getPoint().y;
    //                trgx.setText(""+Hovercraft.main.TX);
    //                trgy.setText(""+Hovercraft.main.TY);
    //                trga.setText(""+Hovercraft.main.TA);
    //        }
    //
    //        /**
    //         * @see java.awt.event.MouseListener#mouseEntered(MouseEvent)
    //         */
    //        public void mouseEntered(MouseEvent arg0) {
    //        }
    //
    //        /**
    //         * @see java.awt.event.MouseListener#mouseExited(MouseEvent)
    //         */
    //        public void mouseExited(MouseEvent arg0) {
    //        }
    //
    //        /**
    //         * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
    //         */
    //        public void mousePressed(MouseEvent arg0) {
    //        }
    //
    //        /**
    //         * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
    //         */
    //        public void mouseReleased(MouseEvent arg0) {
    //        }
    //


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


    /**
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        actionHandler(arg0);
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
     */
    public void mouseClicked(MouseEvent arg0) {
        mouseHandler(arg0);
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(MouseEvent)
     */
    public void mouseEntered(MouseEvent arg0) {
        mouseHandler(arg0);
    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(MouseEvent)
     */
    public void mouseExited(MouseEvent arg0) {
        mouseHandler(arg0);
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
     */
    public void mousePressed(MouseEvent arg0) {
        mouseHandler(arg0);
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
     */
    public void mouseReleased(MouseEvent arg0) {
        mouseHandler(arg0);
    }

}
