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


import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author M.A.A. Sanvido
 * @version StartApplet.java,v 1.6 2004/09/29 03:52:55 cxh Exp
 * @since Giotto 1.0.1
 */
public class StartApplet extends java.applet.Applet implements ActionListener {

    Button open;

    String pinfo[][] = {
        {"ecdfile1",    "string",    "remote ecd file"},
        {"ecdfile2",    "string",    "remote ecd file"},
        {"ecdfile3",    "string",    "remote ecd file"},
        {"ecdfile4",    "string",    "remote ecd file"},
        {"ecdfile5",    "string",    "remote ecd file"},
    };

    //Get a parameter value
    public String getParameter(String key, String def) {
        return getParameter(key);
    }

    //Construct the applet
    public StartApplet() {
    }
    //Initialize the applet
    public void init() {
        this.removeAll();
        open = new Button("Open Emachine");

        //  find ecd file automatically !!!


        this.setLayout(new GridLayout(1,3));

        open.setSize(100,50);
        open.addActionListener(this);
        this.add(open);
        System.out.println("applet started");

    }

    //Start the applet
    public void start() {
    }
    //Stop the applet
    public void stop() {
    }
    //Destroy the applet
    public void destroy() {
    }
    //Get Applet information
    public String getAppletInfo() {
        return "EMachine Applet";
    }
    //Get parameter info
    public String[][] getParameterInfo() {
        return pinfo;
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String par[] = new String[5];
        String str[] = null;
        int n = 0;
        if (obj == open) {
            if (this.getCodeBase().getProtocol().startsWith("http")) {
                par[0] = this.getParameter("ecdfile1");
                if (par[0] != null) { n=1;
                par[1] = this.getParameter("ecdfile2");
                if (par[1]!=null) { n=2;
                par[2] = this.getParameter("ecdfile3");
                if (par[2]!=null) { n=3;
                par[3] = this.getParameter("ecdfile4");
                if (par[3]!=null) { n=4;
                par[4] = this.getParameter("ecdfile5");
                if (par[4]!= null) n=5;
                }
                }
                }
                }
                if (n>0) {
                    str = new String[n+1];
                    str[0] = this.getCodeBase().toString();
                    for (int i =1; i<=n; i++) {
                        str[i] = par[i-1];
                    }
                }
                Start.main(str);
                System.out.println(str.toString());
            } else System.out.println("not possible to load remote file");
        }

    }

}
