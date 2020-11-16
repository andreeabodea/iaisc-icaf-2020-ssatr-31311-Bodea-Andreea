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


import giotto.functionality.code.bool_port;
import giotto.functionality.interfaces.DriverInterface;
import giotto.functionality.table.Parameter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;

/**
 * @author M.A.A. Sanvido
 * @version Elevator.java,v 1.15 2004/10/01 01:09:24 cxh Exp
 * @since Giotto 1.0.1
 */
public class Elevator implements DriverInterface, Serializable {

    static ElevatorFrame frame;

    public static final int CLOSED = 0, OPEN = 1;
    public static final int MIN = 0, MAX = 4;

    static boolean [] button = new boolean[MAX+1];;
    static int position = 0;
    static int door = CLOSED;


    public static boolean Called(int i) {
        if ((MIN <= i) & (i <= MAX)) return button[i];
        else return false;
    }

    public static int Position() {
        return position;
    }

    public static void Reset() {
        int i;
        button = new boolean [MAX+1];
        position = MIN; door = CLOSED;
        for (i=0; i <=MAX; i++) {button[i] = false;}
        UpdateFrame();
    }

    public static void Call(int i) {
        if ((MIN <= i) & (i <= MAX)) {
            button[i] = true;
        }
        UpdateFrame();
    }

    public static void Down() {
        if (position > MIN) position--;
        UpdateFrame();
    }

    public static void Up() {
        if (position < MAX) position++;
        UpdateFrame();
    }

    public static void Idle() {
    }

    public static void Open() {
        door = OPEN;
        if (button[position]) button[position] = false;
        UpdateFrame();
    }

    public static void Close() {
        door = CLOSED;
        UpdateFrame();
    }

    public static void OpenWindow() {
        InitFrame();
        Reset();
    }

    static void InitFrame() {
        frame = new ElevatorFrame();
        frame.validate();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(300, 250);
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

    static void UpdateFrame() {
        frame.floor0.setEnabled(false);
        frame.floor1.setEnabled(false);
        frame.floor2.setEnabled(false);
        frame.floor3.setEnabled(false);
        frame.floor4.setEnabled(false);
        switch (position) {
        case 0: frame.floor0.setEnabled(true); break;
        case 1: frame.floor1.setEnabled(true); break;
        case 2: frame.floor2.setEnabled(true); break;
        case 3: frame.floor3.setEnabled(true); break;
        case 4: frame.floor4.setEnabled(true); break;
        }
        frame.door.setEnabled(door == OPEN);
        frame.called0.setEnabled(button[0]);
        frame.called1.setEnabled(button[1]);
        frame.called2.setEnabled(button[2]);
        frame.called3.setEnabled(button[3]);
        frame.called4.setEnabled(button[4]);
    }

    /**
     * @see giotto.functionality.interfaces.DriverInterface#run(Parameter)
     */
    public void run(Parameter parameter) {
        ((bool_port)parameter.getPortVariable(0)).setBoolValue(true);
        System.out.println("window initialized");
        OpenWindow();
    }

}
