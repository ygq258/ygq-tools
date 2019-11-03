package org.ygq.tools.test;

public class ThreadTest {

	public static void show(String value) {
		Thread t = Thread.currentThread();
		Thread d = new Thread(()-> {
			Thread currentThread = Thread.currentThread();
			System.out.println(currentThread.getName()+":准备执行定时器了:"+value);
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("定时器执行完毕");
		});
		d.start();
	}
	
	public static void main(String[] args) {
		
		Thread d = new Thread(()-> {
			Thread currentThread = Thread.currentThread();
			System.out.println(currentThread.getName()+":准备执行业务");
			show("我调用你了");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("业务执行好了");
		});
		d.start();
		
	}
}
