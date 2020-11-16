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
package platform.emachine.core;

/**
 * @author M.A.A. Sanvido
 * @version EmulatorCore.java,v 1.7 2004/09/29 03:34:44 cxh Exp
 * @since Giotto 1.0.1
 */



import giotto.functionality.table.Condition;
import giotto.functionality.table.Driver;
import giotto.functionality.table.Ecode;
import giotto.functionality.table.Host;
import giotto.functionality.table.TableEntry;
import giotto.functionality.table.Task;
import giotto.functionality.table.Trigger;

import java.util.LinkedList;
import java.util.ListIterator;

public class EmulatorCore {

    final static int waitprog = 0, waitanno = 1, exec = 2, exception = 3;

    /* Machine implementation limitations */
    final static int MAXTASKS = 32;

    //visualization adapter//
    static GuiInterface gui;

    private int localTime = 0;
    private String ServerIP = "";
    private int ServerPort = 29081;

    LinkedList  activeTriggers;// = new LinkedList();

    //The scheduler to handle task scheduling
    Scheduler scheduler;


    public EmulatorCore(Scheduler sched) {
        this(sched, null);
    }


    public EmulatorCore(Scheduler sched, GuiInterface gui0) {
        scheduler = sched;
        this.setGui(gui0);
    }

    public EmulatorCore(Scheduler sched, GuiInterface gui0, String ip, int port) {
        ServerIP = ip; ServerPort = port;
        scheduler = sched;
        this.setGui(gui0);
    }


    public void setScheduler(Scheduler sch) {
        scheduler = sch;
    }

    public void setIp(String ip, int port) {
        ServerIP = ip; ServerPort = port;
    }
    private int getStartAddress() {
        int i = 0;
        Host host;
        while (i < Host.getNumberOf()) {
            host = Host.getHost(i);
            if ((host.getIp().equals(ServerIP))
                    && (host.getPort() == ServerPort))
                { return host.getStartAddress();
                }
            i++;
        }
        return 0;
    }

    //Called when the emachine starts running
    //(either the first time, or after a call to Stop)
    public void Start() {
        this.insertGui();
        scheduler.Start();
        //Clear old active triggers (if any)
        activeTriggers = new LinkedList();

        //Let user know we've started running
        if (TableEntry.CheckTables()) {
            System.out.println("Emulator starts execution.");
            System.out.println("Code size   : " + Ecode.getNumberOf());
            System.out.println("msec per unit   : " + Ecode.getMinUnitPeriod());
        } else {
            System.out.println("Emulator could not find java functionality.");
            return;
        }

        //Start with first instruction i.e. serch for netry address
        pc = getStartAddress();

        //Start at time 0
        localTime = 0;
    }


    //Resets the emachine and removes it from the gui (if any)
    public void Stop() {
        this.removeGui();
        scheduler.Stop();
    }


    //Associates a gui with this emulator
    public void setGui(GuiInterface gui0) {
        EmulatorCore.gui = gui0;

        //this.insertGui();
    }


    private void insertGui() {
        //initialize the task/driver/condition wrappers

        int list;
        TableEntry element;

        list = 0;
        while (list < Driver.getNumberOf()) {
            element = Driver.getDriver(list);
            if (gui != null) gui.addDriver(element.getIndex(), element.getJavaCodeName());
            list++;
        }

        list = 0;
        while (list < Condition.getNumberOf()) {
            element = Condition.getCondition(list);
            if (gui != null) gui.addCondition(element.getIndex(), element.getJavaCodeName());
            list++;
        }

        if (gui != null)
            gui.setTriggersText("");
    }


    private void removeGui() {
        if (gui != null) gui.removeDrivers();
        if (gui != null) gui.removeConditions();
    }

    private int pc = 0;


    private boolean checkCollision(int protection) {
        for (int i=0; i< MAXTASKS; i++) {
            if ((protection % 2 == 1) & (scheduler.TaskPending(i))) return true;
            protection = protection / 2;
        }
        return false;
    }


    private String Remove(int n, String t) {
        char[] ch = new char[1];
        int i = 0, beg = -1, end = -1, j = 0;
        while (i < t.length()) {
            t.getChars(i, i + 1, ch, 0);
            if ((ch[0] == '(') & (beg < 0)) {
                if (j == n) {
                    beg = i;
                } else
                    j++;
            } else if ((ch[0] == ')') & (beg >= 0) & (end < 0)) {
                end = i;
            }
            i++;
        }
        if (beg == 0) return t.substring(end+1);
        else if (beg > 0)
            return t.substring(0,beg-1) + t.substring(end + 1);
        else
            return t;
    }


    //Runs a single block of code
    //passed the time since last call (in ms)
    public void runOnce(int deltaTime) {

        localTime += deltaTime;

        if (gui!=null) gui.setTime(localTime);

        /* evaluate all triggers and find the active one
           if active the trigger it is removed from queue and executed.
        */
        //First, if no instruction to run, check for triggers
        ListIterator i = activeTriggers.listIterator(0);
        while (pc == -1 && i.hasNext()) {
            EmulatedTrigger et = (EmulatedTrigger) i.next();
            if (et.trigger.isEnabled(localTime, et.state, et.delta, Ecode.getMinUnitPeriod())) {
                if (gui!=null)
                    gui.setTriggersText( Remove(i.previousIndex(), gui.getTriggersText()) );

                //Trigger went off - remove it from list
                i.remove();

                //Update pc
                pc = et.dest;
            }
        }

        /* if we have code to run, then run it */
        while (pc != -1) {
            Ecode instr = Ecode.get(pc);
            //                System.out.println("interpreting " + instr);
            switch (instr.getOpcode()) {
            case Ecode.nopCode :
                pc++; /* nop() */
                break;

            case Ecode.futureCode : /* future(cond_n, pc) */
                Trigger trigger = Trigger.getTrigger(instr.getArg1());
                EmulatedTrigger t = new EmulatedTrigger();
                t.trigger = trigger;
                t.dest = instr.getArg2();
                t.delta = instr.getArg3();
                t.state = localTime;
                //Add new trigger to list of active triggers
                activeTriggers.add(t);
                if (gui!=null) gui.setTriggersText(
                        gui.getTriggersText()
                        + "("
                        + instr.getArg1()
                        + ","
                        + instr.getArg2()
                        + ","
                        + (instr.getArg3()+localTime)
                        + ")");
                pc++;
                break;

            case Ecode.callCode :
                /* call(driver_n, from_n, to_n) */
                Driver driver = Driver.getDriver(instr.getArg1());
                if (gui!=null) gui.setDriverColor(instr.getArg1(), GuiInterface.red);
                if (checkCollision(driver.getProtection()))
                    System.out.println("driver time safety violation " + driver.getName());
                //protect driver call
                try {
                    driver.run();
                } catch (RuntimeException e) {
                }

                //                                                        System.out.println("drv: " + driver.getName() +" " + driver.getProtection());
                pc++;
                break;

            case Ecode.scheduleCode :
                /* schedule(driver_n, from_n, to_n, exectime) */
                int taskID = instr.getArg1();

                /* time safety check */
                if (scheduler.TaskPending(instr.getArg1()))
                    System.out.println("time safety violation for task "+Task.getTask(taskID).getName());
                else
                    scheduler.AddTask(taskID, 40 , (instr.getArg3() & 0xFFF), (instr.getArg3() >> 12));

                pc++;
                break;

            case Ecode.ifCode : /* ifthen(cond_n, pc) */
                Condition condition = Condition.getCondition(instr.getArg1());
                if (condition.run())
                    pc = instr.getArg2();
                else
                    pc = instr.getArg3();
                break;

            case Ecode.jumpCode : /* jump(pc) */
                pc = instr.getArg1();
                break;

            case Ecode.returnCode : /* return */
                pc = -1;
                break;
            }
        }
    }
}
