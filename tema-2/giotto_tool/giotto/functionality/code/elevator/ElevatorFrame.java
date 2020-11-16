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
package giotto.functionality.code.elevator;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;

/**
 * @author M.A.A. Sanvido
 * @version ElevatorFrame.java,v 1.15 2004/10/01 01:09:28 cxh Exp
 * @since Giotto 1.0.1
 */
public class ElevatorFrame extends Frame implements ActionListener, WindowListener, Serializable {
    Panel contentPanel = new Panel();
    Label jLabel1 = new Label();
    Button cb4 = new Button();
    Button cb3 = new Button();
    Button cb2 = new Button();
    Button cb1 = new Button();
    Button cb0 = new Button();
    Label floor4 = new Label();
    Label floor3 = new Label();
    Label floor2 = new Label();
    Label floor1 = new Label();
    Label floor0 = new Label();
    Label door = new Label();
    Label called4 = new Label();
    Label called3 = new Label();
    Label called2 = new Label();
    Label called1 = new Label();
    Label called0 = new Label();

    public ElevatorFrame() {
        this.add(contentPanel);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.lightGray);
        this.setTitle("The Elevator");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24));
        jLabel1.setText("Elevator");
        jLabel1.setBounds(new Rectangle(74, 11, 97, 17));

        cb4.setLabel("Call 4");
        cb4.setBounds(new Rectangle(82, 36, 79, 27));
        cb4.addActionListener(this);
        cb3.setLabel("Call 3");
        cb3.setBounds(new Rectangle(82, 66, 79, 27));
        cb3.addActionListener(this);
        cb2.setLabel("Call 2");
        cb2.setBounds(new Rectangle(82, 97, 79, 27));
        cb2.addActionListener(this);
        cb1.setLabel("Call 1");
        cb1.setBounds(new Rectangle(82, 129, 79, 27));
        cb1.addActionListener(this);
        cb0.setLabel("Call 0");
        cb0.setBounds(new Rectangle(82, 161, 79, 27));
        cb0.addActionListener(this);
        floor4.setText("floor4");
        floor4.setBounds(new Rectangle(22, 37, 54, 25));
        floor3.setBounds(new Rectangle(22, 67, 52, 25));
        floor3.setText("floor3");
        floor2.setBounds(new Rectangle(22, 98, 55, 25));
        floor2.setText("floor2");
        floor1.setBounds(new Rectangle(22, 130, 55, 25));
        floor1.setText("floor1");
        floor0.setBounds(new Rectangle(22, 162, 55, 25));
        floor0.setText("floor0");
        door.setText("door open");
        door.setBounds(new Rectangle(83, 187, 103, 25));
        called4.setBounds(new Rectangle(167, 37, 103, 25));
        called4.setText("called4");
        called3.setBounds(new Rectangle(167, 67, 103, 25));
        called3.setText("called3");
        called2.setText("called2");
        called2.setBounds(new Rectangle(167, 98, 103, 25));
        called1.setText("called1");
        called1.setBounds(new Rectangle(167, 130, 103, 25));
        called0.setText("called0");
        called0.setBounds(new Rectangle(167, 162, 103, 25));
        this.contentPanel.add(jLabel1, null);
        this.contentPanel.add(cb4, null);
        this.contentPanel.add(cb3, null);
        this.contentPanel.add(called3, null);
        this.contentPanel.add(called4, null);
        this.contentPanel.add(floor4, null);
        this.contentPanel.add(floor3, null);
        this.contentPanel.add(cb0, null);
        this.contentPanel.add(floor0, null);
        this.contentPanel.add(called0, null);
        this.contentPanel.add(door, null);
        this.contentPanel.add(cb2, null);
        this.contentPanel.add(floor2, null);
        this.contentPanel.add(called2, null);
        this.contentPanel.add(cb1, null);
        this.contentPanel.add(floor1, null);
        this.contentPanel.add(called1, null);
        this.addWindowListener(this);
    }


    public void mouseHandler(MouseEvent e) {
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
        this.hide();
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
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == cb4) Elevator.Call(4);
        if (obj == cb3) Elevator.Call(3);
        if (obj == cb2) Elevator.Call(2);
        if (obj == cb1) Elevator.Call(1);
        if (obj == cb0) Elevator.Call(0);
    }

}
