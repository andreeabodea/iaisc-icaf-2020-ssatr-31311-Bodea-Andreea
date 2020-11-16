/*
  Copyright (c) 2004 The Regents of the University of California.
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
package giotto.functionality.code.hovercraft;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * @author M.A.A. Sanvido
 * @version Map.java,v 1.6 2004/09/29 03:50:52 cxh Exp
 * @since Giotto 1.0.1
 */
public class Map extends Canvas implements Serializable {

    static final int R = 15; // radius
    static final int RP = 20;        // direction arrow

    /**
     * @see java.awt.Component#paint(Graphics)
     */
    public void paint(Graphics g) {
        super.paint(g);
        int x = Math.round(Hovercraft.main.X);
        int y = Math.round(Hovercraft.main.Y);
        double a = Hovercraft.main.A;
        double ta = Hovercraft.main.TA;
        int tx = Math.round(Hovercraft.main.TX);
        int ty = Math.round(Hovercraft.main.TY);
        int ex = Math.round(Hovercraft.main.EX);
        int ey = Math.round(Hovercraft.main.EY);

        int x0 = (int)(RP*Math.cos(a)) + x;
        int y0 = -(int)(RP*Math.sin(a)) + y;

        int tx0 = (int)(R/2*Math.cos(ta)) + tx;
        int ty0 = -(int)(R/2*Math.sin(ta)) + ty;
        int tx1 = (int)(RP*Math.cos(ta)) + tx;
        int ty1 = -(int)(RP*Math.sin(ta)) + ty;


        float ea = (Math.round(Hovercraft.main.EA*100)/100.0f);

        int lm = Math.round(Hovercraft.main.lm);
        int rm = Math.round(Hovercraft.main.rm);
        g.clearRect(0, 0, Map.WIDTH, Map.HEIGHT);
        g.setColor(Color.black);
        g.fillOval(x-R/2, y-R/2, R, R);
        g.drawLine(x, y, x0, y0);
        g.setColor(Color.blue);
        g.drawOval(tx-R/2, ty-R/2, R, R);
        g.drawLine(tx0, ty0, tx1, ty1);

    }

    /**
     * @see java.awt.Component#update(Graphics)
     */
    public void update(Graphics g) {
        super.update(g);
        paint(g);

    }



}
