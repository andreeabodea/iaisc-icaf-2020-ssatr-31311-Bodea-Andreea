/*
  Copyright (c) 2003-2004 The Regents of the University of California.
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

import platform.emachine.core.GuiInterface;
import platform.emachine.core.Scheduler;


/**
 * A scheduler that runs tasks as Java threads.
 * @author M.A.A. Sanvido
 * @version JavaScheduler.java,v 1.7 2004/10/01 01:10:41 cxh Exp
 * @since Giotto 1.0.1
 */
public class JavaScheduler implements Scheduler
{
    /* Machine implementation limitations */
    final static int MAXTASKS = 32;
    boolean[] taskset;

    GuiInterface gui;


    public JavaScheduler() {
        this(null);
    }

    public JavaScheduler(GuiInterface gui0) {
        taskset = new boolean[MAXTASKS];
        this.setGui(gui0);
    }


    public void setGui(GuiInterface gui0) {
        gui = gui0;
    }


    //Add a task to the run queue.
    //taskIndex is the index of the Task to run
    //runTime is the simulated execution time of the task (in ms)
    //deadline is the time limit of the task (in ms)
    public void AddTask(int taskIndex, int runTime, int starttime, int deadline)
    {
        EmulatedTask task = EmulatedTask.getTask(taskIndex);
        task.taskstart(starttime, deadline);
    }


    //Returns true if a task is currently pending in the wait queue
    public boolean TaskPending(int taskIndex) {
        return taskset[taskIndex];
    }


    //Called when simulation starts
    //Should initialize tasks as necessary
    public void Start() {
        EmulatedTask.InitTasks(this, gui);
    }


    //Called when simulation stops
    //Should flush any running tasks
    public void Stop() {
        taskset = new boolean[MAXTASKS];

        EmulatedTask.StopTasks();
    }


    void reportCompletion(EmulatedTask task) {
        taskset[task.index] = false;
    }

    void reportStart(EmulatedTask task) {
        taskset[task.index] = true;
    }
}
