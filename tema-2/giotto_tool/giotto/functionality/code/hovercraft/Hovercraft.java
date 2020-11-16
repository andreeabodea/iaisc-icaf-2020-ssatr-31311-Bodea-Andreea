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

import java.io.Serializable;

/**
 * @author M.A.A. Sanvido
 * @version Hovercraft.java,v 1.9 2004/09/29 03:50:45 cxh Exp
 * @since Giotto 1.0.1
 */
public class Hovercraft extends Thread implements Serializable {

    static boolean skip_port = false;

    static Hovercraft main = new Hovercraft();

    static final float dt = 0.1f;
    static final float
    M = 5.15f, R = 0.124f,
        P = 0.084f, N = 5.5f, J = 0.05f;

    static final float MAXPWR = 80f;
    private float ax, vx, x;
    private float ay, vy, y;
    private float aa, va, a, r;

    float X, Y, A;
    float EX, EY, EA, EA2;

    //        actuator
    float lm, rm;

    float TX, TY, TA;

    //        control

    private float tex, tey, tea, ter, tea2;

    HovercraftFrame update;

    static float eri = 0.0f;

    public static void init(HovercraftFrame update) {
        main =   new Hovercraft();
        main.x =  50.0f; main.y =  -50.0f;
        main.vx = 0.0f; main.vy = 0.0f;

        main.TX =  50.0f; main.TY =  50.0f; main.TA = 0.0f;

        main.lm = 0.0f; main.rm = 0.0f;
        main.update = update;
        main.start();
    }
    public static void kill() {
        main = null;
    }

    // controller !!!
    public static float LimitMotor(float power) {
        if (power > MAXPWR) return MAXPWR;
        if (power < -MAXPWR) return -MAXPWR;
        return power;
    }

    public float LimitAngle(float angle) {
        while (angle > Math.PI) angle -= (float)Math.PI*2;
        while (angle < -Math.PI) angle += (float)Math.PI*2;
        return angle;
    }

    public void computeErrors() {

        float lty = -TY;
        float ltx = TX;

        tex = ltx - x;
        tey = lty - y;

        float ldir = (float)Math.atan2(tey, tex);

        tea = LimitAngle(ldir - a);
        tea2 = LimitAngle(TA - a);

        ter = (float)Math.sqrt(tex*tex + tey*tey);
        //                ter = 0;

        EY = -tey;
        EX = tex;
        EA = tea;
        EA2 = tea2;


    }

    static public float TurnPControl(float ea) {
        return ea*0.01f;
    }

    public float turnPControl() {
        return tea*0.02f;
    }

    static public float PointPControl(float ea2) {
        return ea2*0.005f;
    }

    public float pointPControl() {
        return tea2*0.01f;
    }

    static public float ForwardPIControl(float ex, float ey) {
        float r = (float)Math.sqrt(ex*ex + ey*ey);
        if (r < 1) eri = eri + (float)Math.sqrt(ex*ex + ey*ey);
        else eri = 0;
        return r*0.04f + eri*0.01f;
    }

    static public float NetFPControl(float ex, float ey, float ea, float ea2) {
        return (float)((1+Math.cos(ea2))*Math.sqrt(ex*ex + ey*ey)*0.04);
    }

    static public float NetTPControl(float ex, float ey, float ea, float ea2) {
        if (ea2 == 0) return 0;
        else if (ea2 > 0) return (float)((1-Math.cos(ea2))*0.04);
        return (float)(-(1-Math.cos(ea2))*0.04);
    }

    public float forwardPControl() {
        return ter*0.04f;
    }

    public float netForcePControl() {
        return (float)((1+Math.cos(tea2))*Math.sqrt(tex*tex + tey*tey)*0.04);
    }

    public float netTrustPControl() {
        if (tea2 == 0) return 0;
        return (float)((1-Math.cos(tea2))*tea2/Math.abs(tea2)*0.04);
    }



    public void Apply(float fp, float tp) {
        if (tp > 0) {
            lm = 50*(fp/2 /*- tp/2*/);
            rm = 50*(fp/2 + tp/2);
        } else {
            lm = 50*(fp/2 - tp/2);
            rm = 50*(fp/2 /*+ tp/2*/);
        }
    }


    public void TurnControl() {
        float tp = turnPControl();
        float fp = 0;
        Apply(fp, tp);
    }

    public void ForwardControl() {
        float fp = forwardPControl();
        float tp = 0;
        Apply(fp, tp);
    }

    public void Control() {
        float fp = netForcePControl();
        float tp = netTrustPControl();
        Apply(fp, tp);
    }

    public void IdleControl() {
        float fp = 0;
        float tp = 0;
        Apply(fp, tp);
    }

    public void PointControl() {
        float fp = 0;
        float tp = pointPControl();
        Apply(fp, tp);
    }


    public boolean doTurn() {
        return Math.abs(tea) > 0.1;
    }

    public boolean doPoint() {
        return (doIdle() & Math.abs(tea2) > 0.1);
    }

    public boolean doForward() {
        return (ter > 5) & (Math.abs(tea) <= 0.1);
    }

    public boolean doIdle() {
        return ter <= 5;
    }

    public float getPosX() {
        return x;
    }
    public float getPosY() {
        return y;
    }

    public float getPosA() {
        return a;
    }

    // model of the plant
    public void run () {
        Thread lmain = main;
        while (lmain == main) {
            ax = (-N*vx + (rm + lm)*(float)Math.cos(a))/M;
            ay = (-N*vy + (rm + lm)*(float)Math.sin(a))/M;
            aa = (-P*va + (lm - rm)*R)/J;

            va = va + aa*dt;
            vx = vx + ax*dt;
            vy = vy + ay*dt;

            x = x + vx*dt;
            y = y + vy*dt;
            a = LimitAngle(a + va*dt);
            r = (float)Math.sqrt(x*x + y*y);

            //transform to graph coord
            X = x;
            Y = -y;
            A = a;

            update.update();

            try {Thread.sleep((int)(1000*dt));}
            catch (Exception e) {}
        }
    }

}
