package org.ygq.tools.design.state2;

public class ConcreteState1 extends State {
    @Override
    protected void handle1() {
        //本状态下要处理的逻辑
    }

    @Override
    protected void handle2() {
        //设置当前状态state2
        super.mOrderContext.setCurrentState(Context.STATE2);
        //过渡到状态2
        super.context.handle2();
    }

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return null;
	}
}