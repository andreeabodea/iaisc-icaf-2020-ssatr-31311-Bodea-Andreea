/*
  Copyright (c) 2002-2004 The Regents of the University of California.
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
package giotto.functionality.code;

import giotto.functionality.interfaces.TaskInterface;
import giotto.functionality.table.Host;
import giotto.functionality.table.Parameter;
import giotto.functionality.table.Port;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author M.A.A. Sanvido
 * @version send_to_all_hosts.java,v 1.10 2004/09/29 03:49:25 cxh Exp
 * @since Giotto 1.0.1
 */
public class send_to_all_hosts implements TaskInterface, Serializable {

    /**
     * @see giotto.functionality.interfaces.TaskInterface#run(Parameter)
     */
    private byte[] buf;
    DatagramSocket socket;


    public void run(Parameter parameter) {
        //void send_to_host(host_id_type host_id, port_id_type port_id) {
        //  sprintf(send_buffer, "%u,%d", port_id, *((int *) port_table[port_id].port));
        //
        //  os_udp_send(&message_socket, &host_message_addresses[host_id], sizeof(host_message_addresses[host_id]), send_buffer, SEND_SIZE);
        //}
        if (socket == null) {
            try {
                socket = new DatagramSocket();
            } catch (Exception e) {}
        }
        int i = parameter.getNops();
        Port p = parameter.getPort(0);
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(ba);
            oos.writeInt(1); // object tag
            oos.writeObject(parameter.getPort(0));
            oos.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("broadcast "+i+" " +parameter.getPort(0).getName() + " value: "+p.getPortVariable().toString());

        buf = ba.toByteArray();
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        // sending code
        i = 0;
        while (i < Host.getNumberOf()) {
            packet.setAddress(Host.getHost(i).getInetAddress());
            packet.setPort(Host.getHost(i).getPort());
            try {socket.send(packet);}
            catch (IOException e) {e.printStackTrace();}
            System.out.println("broadcast to "+i+ " "+Host.getHost(i).getInetAddress()+" "+Host.getHost(i).getPort()+" "+parameter.getPort(0).getName() + " value: "+p.getPortVariable().toString());
            i++;
        }
    }

}
