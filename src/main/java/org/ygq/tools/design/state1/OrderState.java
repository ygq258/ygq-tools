package org.ygq.tools.design.state1;

public abstract class OrderState {
	
	abstract void confirm(OrderContext orderContext);
	 
    abstract void modify(OrderContext orderContext);
 
    abstract void pay(OrderContext orderContext);
    
    public static void main(String[] args) {
    	OrderContext orderContext = new OrderContext();
        orderContext.confirm();     //已预定状态>已确认状态
        orderContext.modify();      //已确认状态>已预定状态
        orderContext.confirm();    //已预定状态>已确认状态
        orderContext.pay();       //已确认状态>已锁定状态
        orderContext.modify();    //已锁定状态
	}
}

class OrderedState extends OrderState {
    @Override
    void confirm(OrderContext orderContext) {
        System.out.println("订单已确认");
        orderContext.setState(new ConfirmedState());
    }
 
    @Override
    void modify(OrderContext orderContext) {
        System.out.println("订单已修改");
        orderContext.setState(this);
    }
 
    @Override
    void pay(OrderContext orderContext) {
        System.out.println("预定状态无法完成订单！");
    }
}
 
class ConfirmedState extends OrderState {
    @Override
    void confirm(OrderContext orderContext) {
        System.out.println("订单已确认，请勿重复确认");
    }
 
    @Override
    void modify(OrderContext orderContext) {
        System.out.println("订单已修改，请再次确认");
        orderContext.setState(new OrderedState());
    }
 
    @Override
    void pay(OrderContext orderContext) {
        System.out.println("订单已支付，无法再修改");
        orderContext.setState(new LockedState());
    }
}
 
class LockedState extends OrderState {
    @Override
    void confirm(OrderContext orderContext) {
        System.out.println("订单已锁定");
    }
 
    @Override
    void modify(OrderContext orderContext) {
        System.out.println("订单已锁定");
    }
 
    @Override
    void pay(OrderContext orderContext) {
        System.out.println("订单已锁定");
    }
}