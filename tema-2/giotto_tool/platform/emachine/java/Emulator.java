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
 * @version Emulator.java,v 1.8 2004/09/29 03:34:52 cxh Exp
 * @since Giotto 1.0.1
 */

import giotto.functionality.table.Ecode;
import giotto.functionality.table.Host;
import giotto.functionality.table.StopException;
import giotto.functionality.table.TableEntry;

import platform.emachine.core.EmulatorCore;
import platform.emachine.core.GuiInterface;

import java.io.IOException;
import java.net.InetAddress;


public class Emulator implements Runnable {

    final static int DELTATIME = 100;

    static private int localtime;
    static Thread emachine_interpreter_thread;

    static boolean running = false;
    static Ecode prog;
    static Emulator main;



    //  network
    String localip;
    int localport;

    JavaScheduler scheduler = null;
    EmulatorCore core = null;
    EmulatorServer server = null;

    public static boolean Start(GuiInterface gui0) {
        return Start(gui0, null);
    }

    public static boolean Start(GuiInterface gui0, String port) {
        /* build the data structure for the program execution */
        String localip; int localport;
        if (Ecode.getNumberOf() == 0) {
            System.out.println("No Ecode loaded");
            return false;
        }
        try {
            localip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {localip = "";}
        if (port != null)
            try {localport = Integer.parseInt(port);}
            catch (Exception e) {localport = 0;}
        else localport = 0;

        if (main == null) {
            main = new Emulator(gui0, localip, localport);
        }
        if (emachine_interpreter_thread != null) {
            emachine_interpreter_thread = null;
            while (running) {
            }; /* wait current thread termination */
        }

        main.scheduler.setGui(gui0);
        main.core.setGui(gui0);
        main.core.setIp(localip, localport);

        emachine_interpreter_thread = new Thread(main);
        emachine_interpreter_thread.setPriority(Thread.MAX_PRIORITY);
        emachine_interpreter_thread.setName("RT Emulator");
        emachine_interpreter_thread.start();
        return true;
    }

    public static void Stop(StopException s) {
        if (emachine_interpreter_thread != null) {
            emachine_interpreter_thread = null;
            while (running) {
            }; /* wait current thread termination */

            if (main != null) {
                main.core.Stop();
                if (main.server != null) main.server.setStop();
            }

            if (s != null) {s.last_call(); System.out.println("StopExecution catched and executed!");}
            System.out.println("Local Emulator Stopped");
        }
    }
    /** Wait for the Emulator to stop.  The current thread will Join() on the
     *  simulation thread.  Note that unless the Giotto model throws a
     *  StopException, then the model will continue forever and never return.
     */
    public static void Wait() {
        if (emachine_interpreter_thread != null) {
            try {
                emachine_interpreter_thread.join();
            } catch (InterruptedException ex) {
            }
        }
    }

    public static long getTime() {
        return localtime;
    }

    public Emulator(GuiInterface gui0, String localip, int localport) {
        scheduler = new JavaScheduler(gui0);
        core = new EmulatorCore(scheduler, gui0, localip, localport);
        this.localip = localip;
        this.localport = localport;
        if (Host.getNumberOf() > 0 ) {
            try {
                server = new EmulatorServer(localip, localport);
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        core.Start();
        running = true;
        while (emachine_interpreter_thread != null) {
            core.runOnce(DELTATIME);
            try {
                Thread.sleep(DELTATIME);
            } catch (Exception e) {
            }
            running = false;
        }
    }
    public static void LoadECD(String file) throws Exception {
        TableEntry.loadECode(file);
    }

    public static void LoadRemoteECD(String url) throws Exception {
        TableEntry.loadRemoteECode(url);
    }

    /** Run the Ecode in the given .ecd file.  This method blocks
        until the emachine dies.
    */
    public static void runAndWait(String filename) {
        try {
            platform.emachine.java.Emulator.LoadECD(filename);
            platform.emachine.java.Emulator.Start(null);
            platform.emachine.java.Emulator.Wait();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    /**Main method*/
    public static void main(String[] args) {
        if ((args != null) && (args.length > 0)) {
            try {
                TableEntry.loadECode(args[0]);
                Emulator.Start(null);
            } catch (Exception e) {
                System.out.print("could not open file");
            }

        }
    }
}
