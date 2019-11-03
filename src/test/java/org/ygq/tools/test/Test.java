package org.ygq.tools.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Test extends TestTask{

	public List<String> strList = new ArrayList<>();
	
	@Override
	public void init() {
		
	}

	@Override
	public int count() {
		return 100000;
	}	

	@Override
	public void execution() {
		for (int i = 0; i < 1; i++) {
			String generateOrder = GenerateOrderNo.instance.GenerateOrder(TripOrderTypeEnum.ONLINE);
			strList.add(generateOrder);
//			SnowflakeIdWorker.generateId();
//			strList.add(SnowflakeIdWorker.generateId()+"");
		}
	}
	public static void main(String[] args) {
		
//		GenerateOrderNo.instance.cmdInitOrderNoKey(new String[] {"--sdfsdfsdf=1",null,"","---orderNoKey=我M","--orderNoKey=1他😋X",""});
		Test test = new Test();
		TestExecutor te = new TestExecutor(test);
		te.execute(24);
		te.printReport();
		Set<String> ss = new HashSet<>();
		
		for (int i = 0; i < test.strList.size(); i++) {
			String string = test.strList.get(i);
			if (ss.contains(string)) {
				throw new RuntimeException("重复 "+string);
			}
			ss.add(string);
			if (string.length() != 19) {
				throw new RuntimeException("超长 "+string);
			}
		}
		Iterator<String> iterator = ss.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("sd");
	}

}
