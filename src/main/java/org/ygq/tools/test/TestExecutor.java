package org.ygq.tools.test;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 压力测试执行器
 * @author yang
 */
public class TestExecutor {
	
	private List<Thread> threadList = new ArrayList<>();
	private long initTime = 0;		//初始化所耗费时间
	private long executeTime = 0;	//执行所耗费时间
	private boolean isBandwidthTest = false;	//是否贷款测试
	private TestTask task;			//测试任务
	
	/**
	 * @param threadNumber 线程数量
	 * @param task	测试任务
	 */
	public TestExecutor(TestTask task) {
		this.task = task;
	}
	
	public void setBandwidthTest(boolean isBandwidthTest) {
		this.isBandwidthTest = isBandwidthTest;
	}

	/**
	 * 初始化线程数量
	 * @param threadNumber
	 */
	public void initThread(int threadNumber, Runnable run) {
		threadList.clear();
		for (int i = 0; i < threadNumber; i++) {
			Thread thread = new Thread(run);
			threadList.add(thread);
		}
	}
	
	/**
	 * @return 初始化使用时间ms
	 */
	public long getInitTime(){
		return this.initTime;
	}
	
	/**
	 * 
	 * @return 执行所使用的时间ms
	 */
	public long getExecuteTime() {
		return executeTime;
	}

	/**
	 * 执行测试
	 */
	public void execute(int threadNumber){
		this.task.reset();
		initThread(threadNumber, this.task);
		test();
	}
	
	/**
	 * 具体测试
	 */
	public void test() {
		long startInitTime = System.currentTimeMillis();
		this.task.init();
		this.initTime = System.currentTimeMillis() - startInitTime;
		
		long startExecuteTime = System.currentTimeMillis();
		for (int i = 0; i < threadList.size(); i++) {
			Thread thread = threadList.get(i);
			thread.start();
		}
		for (int i = 0; i < threadList.size(); i++) {
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.executeTime = System.currentTimeMillis() - startExecuteTime;
	}
	
	private String toReportString() {
		NumberFormat nf=NumberFormat.getNumberInstance() ; 
		nf.setMaximumFractionDigits(4);
		
		String text = MessageFormat.format("使用线程数量:{0}个, 初始化耗费时间:{1}ms, 执行耗费时间:{2}ms, 任务总执行次数:{3}次, 每次任务所耗费时间:{4}ms, 每秒执行次数:{5}次 ",
				new Object[] { threadList.size(), 
						this.getInitTime(), 
						this.getExecuteTime(), 
						task.count(),
						nf.format(this.getExecuteTime() / (double) task.count()),
						nf.format(1000d/(this.getExecuteTime() / (double) task.count()))
						});
		if (isBandwidthTest) {
			double sendBytePerSecond = (task.sendByteNumber()/1024d) / (this.getExecuteTime()/1000d);
			double ReceiveBytePerSecond = (task.receiveByteNumber()/1024d) / (this.getExecuteTime()/1000d);
			double sendBytePerCount = (task.sendByteNumber()/(double)this.task.count()/1024d);
			double ReceiveByteCount = (task.receiveByteNumber()/(double)this.task.count()/1024d);
			nf.setMaximumFractionDigits(3);
			String bw = MessageFormat.format("每秒发送数据量:{0}KB, 每秒接收数据量:{1}KB, 平均每次发送数据量:{2}KB, 平均每次接收数据量:{3}KB ", new Object[]{
					nf.format(sendBytePerSecond),
					nf.format(ReceiveBytePerSecond),
					nf.format(sendBytePerCount),
					nf.format(ReceiveByteCount)});
			text += "\n"+bw;
		}
		return text;
	}
	
	/**
	 * 打印报表
	 */
	public void printReport() {
		System.out.println("-----------#####-TEST-#####-----------");
		System.out.println(toReportString());
	}
	
}
