package platform.emachine.core;
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
 * @version EmulatorPanel.java,v 1.9 2004/09/29 03:34:46 cxh Exp
 * @since Giotto 1.0.1
 */

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

//import java.util.ArrayList;


public class EmulatorPanel extends Panel implements GuiInterface, ActionListener {

    Panel task_panel = new Panel();

    Button run_Button = new Button();


    GridLayout driver_grid = new GridLayout(1,1);
    GridLayout task_grid = new GridLayout(1,1);
    GridLayout cond_grid = new GridLayout(1,1);
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    Label time = new Label();
    Label time_str = new Label();
    Label trigger = new Label();
    Label trigger_str = new Label();
    Label task = new Label();
    Label tasks_str = new Label();
    Label error_str = new Label();
    Label ip = new Label();
    Label port_str = new Label();
    TextField port = new TextField();


    /**Tasks, Drivers*/
    ArrayList tasklist = new ArrayList();
    ArrayList driverlist = new ArrayList();
    ArrayList conditionlist = new ArrayList();

    public EmulatorPanel() {

        try {

            this.setSize(new Dimension(400, 450));
            this.setLayout(gridbag);

            task_panel.setBackground(Color.green);
            //                        task_panel.setBorder(titledBorder2);
            //                        task_panel.setDoubleBuffered(false);
            task_panel.setSize(new Dimension(250, 80));
            task_panel.setLayout(task_grid);


            run_Button.setSize(new Dimension(180, 30));
            run_Button.setLabel("Simulate");
            run_Button.addActionListener(this);

            time.setText("Time:");
            time.setBounds(new Rectangle(224, 248, 41, 17));
            time_str.setText("0");
            //                        time_str.setBounds(new Rectangle(326, 248, 124, 17));

            trigger.setText("Trigger Queue:");
            //                        trigger.setBounds(new Rectangle(224, 269, 88, 17));
            trigger_str.setText("");
            //                        trigger_str.setBounds(new Rectangle(326, 269, 123, 17));

            task.setText("Active Tasks:");
            task.setBounds(new Rectangle(224, 291, 87, 17));
            tasks_str.setText("");
            //                        tasks_str.setBounds(new Rectangle(326, 291, 126, 17));
            this.setBackground(Color.lightGray);
            error_str.setForeground(Color.red);
            //                        error_str.setBounds(new Rectangle(225, 329, 189, 17));
            String ips = InetAddress.getLocalHost().getHostAddress();
            ip.setText("Ip: "+ips);
            port_str.setText("port:");
            port.setText("41254");

            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.BOTH;
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.add(task_panel, c);
            c.gridwidth = 1;
            this.add(task, c);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.add(tasks_str, c);
            c.gridwidth = 1;
            this.add(time, c);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.add(time_str, c);
            c.gridwidth = GridBagConstraints.REMAINDER;

            c.gridwidth = 1;
            this.add(trigger, c);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.add(trigger_str, c);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.add(error_str, c);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.add(ip, c);
            c.gridwidth = 1;
            this.add(port_str, c);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.add(port, c);

            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.NONE;
            this.add(run_Button, c);
            this.validate();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Tells the gui whether or not the simulation is running
    public void setStoppable(boolean stoppable) {
        run_Button.setVisible(stoppable);

        this.validate();
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        Object obj = arg0.getSource();
        if (obj == run_Button) {
            if (run_Button.getLabel().equalsIgnoreCase("simulate")) {
                if (platform.emachine.java.Emulator.Start(this, port.getText()))
                    run_Button.setLabel("Stop");
                this.validate();
            } else {
                platform.emachine.java.Emulator.Stop(null);
                run_Button.setLabel("Simulate");
                this.validate();
            }
        }
    }

    /**
     * @see platform.emachine.core.GuiInterface#getTriggersText()
     */
    public String getTriggersText() {
        return trigger_str.getText();
    }

    /**
     * @see platform.emachine.core.GuiInterface#setTime(int)
     */
    public void setTime(int time) {
        time_str.setText("" + time);
    }

    /**
     * @see platform.emachine.core.GuiInterface#setTriggersText(String)
     */
    public void setTriggersText(String str) {
        trigger_str.setText(str);
    }

    /**
     * @see platform.emachine.core.GuiInterface#setDriverColor(int, Color)
     */
    public void setDriverColor(int i, Color color) {
    }


    /**
     * @see platform.emachine.core.GuiInterface#addTask(int, String)
     */
    public void addTask(int i, String string) {
        Label label = new Label(string);
        if (task_grid.getRows() < i+1) task_grid.setRows(i+1);
        tasklist.add(i, label);
        task_panel.add(label);
        task_panel.validate();
    }

    /**
     * @see platform.emachine.core.GuiInterface#removeTasks()
     */
    public void removeTasks() {
        tasklist.clear();
        task_panel.removeAll();
    }

    /**
     * @see platform.emachine.core.GuiInterface#addDriver(int, String)
     */
    public void addDriver(int i, String string) {
        Label label = new Label(string);
        if (driver_grid.getRows() < i+1) driver_grid.setRows(i+1);
        //driverlist.add(i, label);
    }

    /**
     * @see platform.emachine.core.GuiInterface#removeDrivers()
     */
    public void removeDrivers() {
        driverlist.clear();
    }

    /**
     * @see platform.emachine.core.GuiInterface#addCondition(int, String)
     */
    public void addCondition(int i, String string) {
        Label label = new Label(string);
        if (cond_grid.getRows() < i+1) cond_grid.setRows(i+1);
        //conditionlist.add(i, label);
    }

    /**
     * @see platform.emachine.core.GuiInterface#removeConditions()
     */
    public void removeConditions() {
        conditionlist.clear();
    }


    /**
     * @see platform.emachine.core.GuiInterface#setTaskActive(int)
     */
    public void setTaskActive(int i) {
        //((Label)(tasklist.get(i))).setForeground(java.awt.Color.red);
        tasks_str.setText(Integer.toString(i));
    }


    /**
     * @see platform.emachine.core.GuiInterface#setTaskEnded(int)
     */
    public void setTaskEnded(int i) {
        //((Label)(tasklist.get(i))).setVisible(false);
    }

    /**
     * @see platform.emachine.core.GuiInterface#setTaskPassive(int)
     */
    public void setTaskPassive(int i) {
        //((Label)(tasklist.get(i))).setForeground(java.awt.Color.red);
    }

}

class ArrayList {

    Object[] obj = new Object[256];
    int n = 0;

    void clear() {
        n = 0;
    }

    void add(Object o) {
        obj[n] = o; n++;
    }

    void add(int i, Object o) {
        obj[i] = o;
    }
}



