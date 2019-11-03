package org.ygq.tools.design.state1;

/**
 * 状态模式
 * @author ygq
 *
 */
public class OrderContext {
	
	OrderState state = null;

	// 新建订单设为已预定状态
	OrderContext() {
		this.state = new OrderedState();
	}

	void setState(OrderState state) {
		this.state = state;
	}

	public void confirm() {
		state.confirm(this);
	}

	public void modify() {
		state.modify(this);
	}

	public void pay() {
		state.pay(this);
	}
}
