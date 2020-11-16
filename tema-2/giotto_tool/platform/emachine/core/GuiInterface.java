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
 * @version GuiInterface.java,v 1.7 2004/09/29 03:34:48 cxh Exp
 * @since Giotto 1.0.1
 */

import java.awt.Color;


public interface GuiInterface {

    final static Color red = Color.red;
    final static Color green = Color.green;
    final static Color yellow = Color.yellow;

    /**
     * Method .setTime
     * @param time
     */
    public void setTime(int time);

    /**
     * Method getTriggersText.
     * @return String
     */
    public String getTriggersText();

    /**
     * Method setTriggersText.
     * @param string
     */
    void setTriggersText(String string);

    /**
     * Method setDrivers.
     * @param i
     * @param color
     */
    void setDriverColor(int i, Color color);

    /**
     * Method getTasksText.
     * @return String
     */

    void setTaskActive(int i);

    /**
     * Method setTaskPassive.
     * @param i
     */
    void setTaskPassive(int i);

    /**
     * Method setTaskEnded.
     * @param i
     */
    void setTaskEnded(int i);

    /**
     * Method addTask.
     * @param i
     * @param string
     */
    void addTask(int i, String string);

    /**
     * Method addDriver.
     * @param string
     */
    void addDriver(int i, String string);

    /**
     * Method removeTasks.
     */
    void removeTasks();

    /**
     * Method removeDrivers.
     */
    void removeDrivers();

    /**
     * Method addDriver.
     * @param string
     */
    void addCondition(int i, String string);

    /**
     * Method removeDrivers.
     */
    void removeConditions();


}
