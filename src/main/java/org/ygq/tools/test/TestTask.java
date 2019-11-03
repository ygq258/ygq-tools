package org.ygq.tools.test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试任务
 * @author yang
 */
public abstract class TestTask implements Runnable {
	
	private AtomicLong sendAl = new AtomicLong(0);
	
	private AtomicLong receiveAl = new AtomicLong(0);
	/**
	 * 要执行任务的初始化方法
	 */
	public abstract void init();
	
	/**
	 * 执行次数
	 * @return
	 */
	public abstract int count();
	
	/**
	 * 要执行的任务
	 */
	public abstract void execution();
	
	private Object lock = new Object();
	
	// 当前执行次数
	private volatile int index = 0;
	
	/**
	 * 重置数据
	 */
	public void reset() {
		index = 0;
		sendAl.set(0);
		receiveAl.set(0);
	}
	
	@Override
	public void run() {
		
		while(true){
			synchronized(lock){
				if (++index > count()) {
					break;
				}
			}
			execution();
		}
	}

	public int getIndex() {
		return index;
	}

	/**
	 * @return 返回每次执行发送字节数
	 */
	public long sendByteNumber() {
		return sendAl.get();
	}
	
	/**
	 * @return 返回每次执行接收的字节数
	 */
	public long receiveByteNumber() {
		return receiveAl.get();
	}
	
	/**
	 * @param number 计算发送数据量
	 */
	public void addSendByteNumber(int number) {
		sendAl.addAndGet(number);
	}
	
	/**
	 * 
	 * @param number 计算接收数据量
	 */
	public void addReceiveByteNumber(int number) {
		receiveAl.addAndGet(number);
	}
	
	
}
