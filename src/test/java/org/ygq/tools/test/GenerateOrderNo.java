package org.ygq.tools.test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 名称： GenerateOrderNo
 * <br>描述：生成唯一订单号，生成年月日加自增数,每毫秒最多可以生成9999个订单号	
 * @author xxu
 * @date 2018年10月9日 上午10:44:21 
 * @version 1.0
 * <br>Copyright by squirrel.
 * <br>Program Name:
 */
public enum GenerateOrderNo {

	instance;
	
	/**
	 * 网络订单前缀
	 */
	public final static String ORDER_PREFIX_NETWORK = "N";
	/**
	 * 线下订单前缀
	 */
	public final static String ORDER_PREFIX_UNDER = "U";
	/**
	 * 普通订单前缀
	 */
	public final static String ORDER_PREFIX_GENERAL = "D";
	
	/**
	 * 订单随机因子数组
	 */
	private final static String[] KEY_VALUES = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	// 全局自增数
	private static int count = 0;
	// 每毫秒秒最多生成多少订单(最好是像9999这种准备进位的值)
	private static final int total = 99;
	// 格式化的时间字符串
	private static final SimpleDateFormat simpleSdf = new SimpleDateFormat("yyMMddHHmmssSSS");
	// 记录上一次的时间,用来判断是否需要递增全局数
	private static String LAST_NOW_STR = null;
	// 订单号生成因子参数名称
	private static String ORDER_NO_KEY_NAME = "--orderNoKey=";
	// 订单号生成因子
	private String orderNoKey = null;
	
	/**
	 * 初始化订单号生成Key
	 * @param orderNoKey
	 */
	public synchronized boolean initOrderNoKey(String orderNoKey) {
		if (orderNoKey == null || orderNoKey.trim().isEmpty()) {
			return false;
		}
		Set<String> factor = new HashSet<>(Arrays.asList(KEY_VALUES));
		for (int i = 0; i < orderNoKey.length(); i++) {
			String key = String.valueOf(orderNoKey.charAt(i));
			if (factor.contains(key)) {
				this.orderNoKey = key;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 命令行初始化
	 * @param args
	 */
	public void cmdInitOrderNoKey(String[] args) {
		if (args == null) {
			return;
		}
		for (int i = 0; i < args.length; i++) {
			String value = args[i];
			if (value == null || value.trim().isEmpty()) {
				continue;
			}
			if (value.indexOf(ORDER_NO_KEY_NAME) == 0) {
				if (initOrderNoKey(value.substring(ORDER_NO_KEY_NAME.length()))) {
					break;
				}
			}
		}
	}
	
	/**
	 * 获取订单前缀与当前时间年月日时分秒毫秒字符串
	 * @param orderType 
	 * @return
	 */
	private StringBuilder getOrderNoPrefix(TripOrderTypeEnum orderType) {
		StringBuilder prefix = new StringBuilder();
		if (orderType == null) {
			prefix.append(ORDER_PREFIX_GENERAL);	// 通用订单号
		} else if (TripOrderTypeEnum.ONLINE.equals(orderType)) {
			prefix.append(ORDER_PREFIX_NETWORK); // 网络订单
		} else {
			prefix.append(ORDER_PREFIX_UNDER); // 线下订单
		}
		if (orderNoKey != null) {
			prefix.append(orderNoKey);
		}else {
			prefix.append(KEY_VALUES[new Random().nextInt(36)]);
		}
		return prefix;
	}

	
	/**
	 * 生成一个普通订单号
	 * @return
	 */
	public String GenerateOrder() {
		return GenerateOrder(null);
	}
	
	/**
	 * 根据订单类型的枚举生成一个订单号  
	 * ygq updated in 2019-05-11
	 * @param orderType
	 * @return
	 */
	public synchronized String GenerateOrder(TripOrderTypeEnum orderType) {
		
		StringBuilder orderNoPrefix = getOrderNoPrefix(orderType);
		/**添加时间戳，并控制计数器*/
		String now = simpleSdf.format(new Date());
		if (!now.equals(LAST_NOW_STR)) {
			count = 0;
			LAST_NOW_STR = now;
		}
		orderNoPrefix.append(LAST_NOW_STR);
		if (++count > total) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return GenerateOrder(orderType);
		}
		int countInt = String.valueOf(total).length() - String.valueOf(count).length();	//算补位
		for (int i = 0; i < countInt; i++) {
			orderNoPrefix.append("0");
		}
		orderNoPrefix.append(count);
		
		return orderNoPrefix.toString();
	}
	
}
