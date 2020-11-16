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
package platform.emachine.java;

/**
 * @author M.A.A. Sanvido
 * @version EmulatedTask.java,v 1.7 2004/09/29 03:34:50 cxh Exp
 * @since Giotto 1.0.1
 */

import giotto.functionality.table.StopException;
import giotto.functionality.table.Task;

import platform.emachine.core.GuiInterface;


class EmulatedTask extends Thread {

    static GuiInterface gui;
    static EmulatedTask[] tasks;
    static JavaScheduler machine;

    Task task;
    long starttime;
    long deadline;
    int state;
    int index;

    static final int NEW = -1, IDLE = 0, ACTIVE = 1, SUSPENDED = 2, KILL = 3;

    EmulatedTask(int i, Task task) {
        this.state = NEW;
        this.task = task;
        this.index = i;
        this.setName(task.getName());
        start();
    }

    synchronized void taskstart(int starttime, int deadline) {
        machine.reportStart(this);
        this.state = ACTIVE;
        this.starttime = Emulator.getTime() + starttime;
        this.deadline = Emulator.getTime() + deadline;
        if (gui != null) gui.setTaskActive(this.index);
        this.notifyAll();
    }

    synchronized void taskend() {
        machine.reportCompletion(this);
        this.state = IDLE;
        if (gui != null) gui.setTaskPassive(this.index);
        try {
            this.wait();
        } catch (InterruptedException e) {
            this.state = ACTIVE;
        }
    }

    synchronized void tasksuspend() {
        this.state = SUSPENDED;
        if (gui != null) gui.setTaskPassive(this.index);
        try {
            this.wait();
        } catch (InterruptedException e) {
            this.state = ACTIVE;
        }
    }

    synchronized void taskkill() {
        this.state = KILL;
        //                if (gui != null) gui.setTaskEnded(this.index);
        this.notifyAll();
    }

    public void run() {
        taskend();
        while (state != KILL) {
            // wait for task start
            if (Emulator.getTime() < this.starttime) {
                try {Thread.sleep(this.starttime-Emulator.getTime());}
                catch (InterruptedException e) {}
            }
            // release it
            try {task.run();}
            catch (StopException s) {
                Emulator.Stop(s);
            }
            // run time check
            if (this.deadline < Emulator.getTime()) System.out.println("deadline overrun");
            //                  try {this.sleep(deadline);}
            //                  catch (InterruptedException e) {};
            taskend();
        }
    }

    protected void finalize() {
        System.out.println("killing task " + task.getJavaCodeName());
        taskkill();
    }


    // static methods
    /**
     * Method getTask.
     * @param i
     * @return Task
     */
    public static EmulatedTask getTask(int i) {
        return tasks[i];
    }

    public static void InitTasks(JavaScheduler machine, GuiInterface gui) {
        int i;
        EmulatedTask.gui = gui;
        EmulatedTask.machine = machine;
        tasks = new EmulatedTask[giotto.functionality.table.Task.getNumberOf()];
        for (i = 0; i < tasks.length; i++) {
            tasks[i] = new EmulatedTask(i, Task.getTask(i));
            if (gui != null) {
                gui.addTask(i, tasks[i].task.getJavaCodeName());
            }
        }
    }

    /**
     * Method stopTasks.
     */
    public static void StopTasks() {
        if (gui != null) gui.removeTasks();
        int i;
        if (tasks != null) {
            for (i = 0; i < tasks.length; i++) {
                tasks[i].taskkill();
            }
        }
    }

}
