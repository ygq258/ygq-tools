package org.ygq.tools.design.state2;

public class Context {
    public static final State STATE1 = new ConcreteState1();
    public static final State STATE2 = new ConcreteState2();

    private State currentState;

    public void setCurrentState(State state1) {
        currentState = state1;
        currentState.setContext(this);
    }

    //行为委托
    public void handle1() {
        this.currentState.handle1();
    }

    public void handle2() {
        this.currentState.handle2();
    }
}
