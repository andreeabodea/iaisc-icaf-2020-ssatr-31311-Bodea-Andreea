package Capsules;

import Ports.Port2;
import StateMachines.StateMachineB;

public class CapsB extends Capsule {
    public Port2 port2;
    public StateMachineB stateMachineB;

    public CapsB() {
        port2 = new Port2();
        stateMachineB = new StateMachineB();
    }
}

