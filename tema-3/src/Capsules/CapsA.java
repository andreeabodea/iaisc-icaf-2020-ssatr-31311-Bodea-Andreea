package Capsules;

import Ports.Port1;
import StateMachines.StateMachineA;

public class CapsA extends Capsule{
    public Port1 port1;
    public StateMachineA stateMachineA;

    public CapsA() {
        port1 = new Port1();
        stateMachineA = new StateMachineA();
    }
}

