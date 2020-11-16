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
/*
 * Created on Apr 4, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package platform.emachine.java;

import giotto.functionality.table.Port;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author M.A.A. Sanvido
 * @version EmulatorServer.java,v 1.7 2004/09/29 03:52:52 cxh Exp
 * @since Giotto 1.0.1
 */
public class EmulatorServer extends Thread {

    private DatagramSocket socket = null;
    private boolean stop = false;
    private byte[] buf = new byte[1024];


    public EmulatorServer(String localip, int port) throws IOException {
        super();
        stop = false;
        socket = new DatagramSocket(port);
    }

    protected void finalize() throws Throwable {
        stop = true;
        super.finalize();
    }

    public void setStop() {
        stop = true;
    }

    public void run() {
        System.out.println("Server emachine started");
        while (!stop) {
            try {
                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                if (ois.readInt() == 1) {
                    Port p = (Port)ois.readObject();
                    // update the value immidiately
                    Port.getPort(p.getIndex()).getPortVariable().copyValueFrom(p.getPortVariable());
                    System.out.println("received update for: " +p.getName());
                } else {
                    int time = ois.readInt();
                }
            } catch (Exception e) {
                e.printStackTrace();
                stop = true;
            }
        }
        socket.close();
    }
}

