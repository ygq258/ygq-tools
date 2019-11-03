package org.ygq.tools.test.jtd;

import java.sql.Time;
import java.util.Date;

public class Test2 {

	public static void main(String[] args) {
		
		System.out.println(String.format("购买【%s】的数量与联系人的数量不符", "门票"));
		
		Date date = new Date();
		
		Time time = new Time(date.getTime());
		
		System.out.println(time);
	}
}
