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

import platform.emachine.core.EmulatorPanel;
import platform.emachine.core.GuiInterface;
import platform.emachine.java.Emulator;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author M.A.A. Sanvido
 * @version EmachineGUI.java,v 1.14 2004/09/29 03:34:32 cxh Exp
 * @since Giotto 1.0.1
 */
class EmachineGUI extends Frame implements GuiInterface, ActionInterface, WindowListener {
    EmulatorPanel contentPane = new EmulatorPanel();

    public EmachineGUI() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            this.add(contentPane);
            this.addWindowListener(this);
            contentPane.setSize(new Dimension(400, 400));
            this.setTitle("E code Interpreter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#setTime(int)
     */
    public void setTime(int time) {
        contentPane.setTime(time);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#getTriggersText()
     */
    public String getTriggersText() {
        return contentPane.getTriggersText();
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#setTriggersText(java.lang.String)
     */
    public void setTriggersText(String string) {
        contentPane.setTriggersText(string);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#setDriverColor(int, java.awt.Color)
     */
    public void setDriverColor(int i, Color color) {
        contentPane.setDriverColor(i, color);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#setTaskActive(int)
     */
    public void setTaskActive(int i) {
        contentPane.setTaskActive(i);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#setTaskPassive(int)
     */
    public void setTaskPassive(int i) {
        contentPane.setTaskPassive(i);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#setTaskEnded(int)
     */
    public void setTaskEnded(int i) {
        contentPane.setTaskEnded(i);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#addTask(int, java.lang.String)
     */
    public void addTask(int i, String string) {
        contentPane.addTask(i, string);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#addDriver(int, java.lang.String)
     */
    public void addDriver(int i, String string) {
        contentPane.addDriver(i,string);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#removeTasks()
     */
    public void removeTasks() {
        contentPane.removeTasks();
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#removeDrivers()
     */
    public void removeDrivers() {
        contentPane.removeDrivers();
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#addCondition(int, java.lang.String)
     */
    public void addCondition(int i, String string) {
        contentPane.addCondition(i,string);
    }

    /* (non-Javadoc)
     * @see platform.emachine.core.GuiInterface#removeConditions()
     */
    public void removeConditions() {
        contentPane.removeConditions();
    }

    /* (non-Javadoc)
     * @see giotto.gdk.ActionInterface#actionHandler(java.awt.event.ActionEvent)
     */
    public void actionHandler(ActionEvent e) {
        contentPane.actionPerformed(e);
    }

    /* (non-Javadoc)
     * @see giotto.gdk.ActionInterface#mouseHandler(java.awt.event.MouseEvent)
     */
    public void mouseHandler(MouseEvent e) {

    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent arg0) {

    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent arg0) {
        this.hide();
        Emulator.Stop(null);
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent arg0) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent arg0) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent arg0) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent arg0) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent arg0) {
    }

}
//
//class EmachineGUI2 extends Frame implements GuiInterface, ActionInterface, WindowListener {
//
//        Panel contentPane = new Panel();
//        Panel cond_panel = new Panel();
//        Panel task_panel = new Panel();
//        Panel driver_panel = new Panel();
//        Panel empty_panel = new Panel();
//
//        Button run_Button = new Button();
//
//
//        GridLayout driver_grid = new GridLayout(1,1);
//        GridLayout task_grid = new GridLayout(1,1);
//        GridLayout cond_grid = new GridLayout(1,1);
//        GridBagLayout gridbag = new GridBagLayout();
//        GridBagConstraints c = new GridBagConstraints();
//
//        Label time = new Label();
//        Label time_str = new Label();
//        Label trigger = new Label();
//        Label trigger_str = new Label();
//        Label task = new Label();
//        Label tasks_str = new Label();
//        Label error_str = new Label();
//
//        boolean novisual = true;
//
//        /**Tasks, Drivers*/
//        ArrayList tasklist = new ArrayList();
//        ArrayList driverlist = new ArrayList();
//        ArrayList conditionlist = new ArrayList();
//
//        public EmachineGUI2() {
//                enableEvents(AWTEvent.WINDOW_EVENT_MASK);
//
//                try {
////                        JPanel contentPane = (JPanel) this.getContentPane();
//                        this.add(contentPane);
//                        this.addWindowListener(this);
//
//                        contentPane.setSize(new Dimension(400, 400));
//                        contentPane.setLayout(gridbag);
//
//                        cond_panel.setBackground(Color.yellow);
////                        cond_panel.setBorder(titledBorder1);
////                        cond_panel.setDoubleBuffered(false);
//                        cond_panel.setSize(new Dimension(250, 80));
//                        cond_panel.setLayout(cond_grid);
//
//                        task_panel.setBackground(Color.green);
////                        task_panel.setBorder(titledBorder2);
////                        task_panel.setDoubleBuffered(false);
//                        task_panel.setSize(new Dimension(250, 80));
//                        task_panel.setLayout(task_grid);
//
//                        driver_panel.setBackground(Color.orange);
////                        driver_panel.setBorder(titledBorder3);
//                        driver_panel.setSize(new Dimension(250, 80));
//                        driver_panel.setLayout(driver_grid);
//
//                        empty_panel.setBackground(Color.orange);
//                        empty_panel.setSize(new Dimension(250, 80));
//
//                        run_Button.setSize(new Dimension(180, 30));
//                        run_Button.setLabel("Simulate");
//                        run_Button.addActionListener(new ActionAdapter(this));
//
//                        time.setText("Time:");
//                        time.setBounds(new Rectangle(224, 248, 41, 17));
//                        time_str.setText("0");
////                        time_str.setBounds(new Rectangle(326, 248, 124, 17));
//
//                        trigger.setText("Trigger Queue:");
////                        trigger.setBounds(new Rectangle(224, 269, 88, 17));
//                        trigger_str.setText("");
////                        trigger_str.setBounds(new Rectangle(326, 269, 123, 17));
//
//                        task.setText("Active Tasks:");
//                        task.setBounds(new Rectangle(224, 291, 87, 17));
//                        tasks_str.setText("");
////                        tasks_str.setBounds(new Rectangle(326, 291, 126, 17));
//                        contentPane.setBackground(Color.lightGray);
//                        error_str.setForeground(Color.red);
////                        error_str.setBounds(new Rectangle(225, 329, 189, 17));
//
//                        c.anchor = GridBagConstraints.WEST;
//                        c.fill = GridBagConstraints.BOTH;
//                        c.gridwidth = GridBagConstraints.REMAINDER;
//                       contentPane.add(task_panel, c);
//                        if (!novisual) contentPane.add(driver_panel, c);
//                        if (!novisual) contentPane.add(cond_panel, c);
////                        if (novisual) contentPane.add(empty_panel, c);
//                        c.gridwidth = 1;
//                        contentPane.add(task, c);
//                        c.gridwidth = GridBagConstraints.REMAINDER;
//                        contentPane.add(tasks_str, c);
//                        c.gridwidth = 1;
//                        contentPane.add(time, c);
//                        c.gridwidth = GridBagConstraints.REMAINDER;
//                        contentPane.add(time_str, c);
//                        c.gridwidth = GridBagConstraints.REMAINDER;
//
//                        c.gridwidth = 1;
//                        contentPane.add(trigger, c);
//                        c.gridwidth = GridBagConstraints.REMAINDER;
//                        contentPane.add(trigger_str, c);
//                        c.gridwidth = GridBagConstraints.REMAINDER;
//                        contentPane.add(error_str, c);
//
//                        c.anchor = GridBagConstraints.CENTER;
//                        c.fill = GridBagConstraints.NONE;
//                        contentPane.add(run_Button, c);
//                        contentPane.validate();
//                        this.setTitle("E code Interpreter");
//
//
//                } catch (Exception e) {
//                        e.printStackTrace();
//                }
//
//        }
//
//        public void actionHandler(ActionEvent e) {
//                if (run_Button.getLabel().equalsIgnoreCase("simulate")) {
//                        if (Emulator.Start(this))
//                                run_Button.setLabel("Stop");
//                                contentPane.validate();
//                                this.pack();
//                } else {
//                        Emulator.Stop(null);
//                        run_Button.setLabel("Simulate");
//                        contentPane.validate();
//                        this.pack();
//                }
//        }
//
//        public void mouseHandler(MouseEvent e) {
//        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#getTriggersText()
//         */
//        public String getTriggersText() {
//                return trigger_str.getText();
//        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#setTime(int)
//         */
//        public void setTime(int time) {
//                time_str.setText("" + time);
//        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#setTriggersText(String)
//         */
//        public void setTriggersText(String str) {
//                trigger_str.setText(str);
//        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#setDriverColor(int, Color)
//         */
//        public void setDriverColor(int i, Color color) {
//        }
//
//
//        /**
//         * @see platform.emachine.core.GuiInterface#addTask(String)
//         */
//        public void addTask(int i, String string) {
//                Label label = new Label(string);
//                if (task_grid.getRows() < i+1) task_grid.setRows(i+1);
//                tasklist.add(i, label);
//                task_panel.add(label);
//                task_panel.validate();
//        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#removeDrivers(String)
//         */
//        public void removeTasks() {
//                tasklist.clear();
//                task_panel.removeAll();
//        }
//
//         /**
//         * @see platform.emachine.core.GuiInterface#addDriver(String)
//         */
//        public void addDriver(int i, String string) {
//                Label label = new Label(string);
//                if (driver_grid.getRows() < i+1) driver_grid.setRows(i+1);
//                driverlist.add(i, label);
//                driver_panel.add(label);
//                driver_panel.validate();
//        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#removeDrivers(String)
//         */
//        public void removeDrivers() {
//                driverlist.clear();
//                driver_panel.removeAll();
//        }
//
//          /**
//         * @see platform.emachine.core.GuiInterface#addCondition(String)
//         */
//        public void addCondition(int i, String string) {
//                Label label = new Label(string);
//                if (cond_grid.getRows() < i+1) cond_grid.setRows(i+1);
//                conditionlist.add(i, label);
//                cond_panel.add(label);
//                cond_panel.validate();
//        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#removeConditions(String)
//         */
//        public void removeConditions() {
//                conditionlist.clear();
//                cond_panel.removeAll();
//        }
//
//
//        /**
//         * @see platform.emachine.core.GuiInterface#setTaskActive(int)
//         */
//        public void setTaskActive(int i) {
//                ((Label)(tasklist.get(i))).setForeground(java.awt.Color.red);
//                tasks_str.setText(Integer.toString(i));
//        }
//
////        String Trim(String s, int i) {
////                StringBuffer  buf = new StringBuffer(s);
////                int l = buf.length();
////                while (l > 1) {
////                        l--;
////                        if (buf.charAt(l) == ' ') {
////                                return buf.substring(0, l-1);
////                        }
////                }
////                return "";
////        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#setTaskEnded(int)
//         */
//        public void setTaskEnded(int i) {
//                ((Label)(tasklist.get(i))).setVisible(false);
//        }
//
//        /**
//         * @see platform.emachine.core.GuiInterface#setTaskPassive(int)
//         */
//        public void setTaskPassive(int i) {
//                ((Label)(tasklist.get(i))).setForeground(java.awt.Color.red);
//        }
//
//
//        /**
//         * @see java.awt.event.WindowListener#windowActivated(WindowEvent)
//         */
//        public void windowActivated(WindowEvent arg0) {
//        }
//
//        /**
//         * @see java.awt.event.WindowListener#windowClosed(WindowEvent)
//         */
//        public void windowClosed(WindowEvent arg0) {
//        }
//
//        /**
//         * @see java.awt.event.WindowListener#windowClosing(WindowEvent)
//         */
//        public void windowClosing(WindowEvent arg0) {
//                this.hide();
//                Emulator.Stop(null);
//        }
//
//        /**
//         * @see java.awt.event.WindowListener#windowDeactivated(WindowEvent)
//         */
//        public void windowDeactivated(WindowEvent arg0) {
//        }
//
//        /**
//         * @see java.awt.event.WindowListener#windowDeiconified(WindowEvent)
//         */
//        public void windowDeiconified(WindowEvent arg0) {
//        }
//
//        /**
//         * @see java.awt.event.WindowListener#windowIconified(WindowEvent)
//         */
//        public void windowIconified(WindowEvent arg0) {
//        }
//
//        /**
//         * @see java.awt.event.WindowListener#windowOpened(WindowEvent)
//         */
//        public void windowOpened(WindowEvent arg0) {
//        }
//
//}
//
//
