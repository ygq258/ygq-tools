package org.ygq.tools.test.jtd;

import java.util.Random;

public class Test {

	public static void main(String[] args) throws Exception {
		
		
//		RandomAccessFile raf = new RandomAccessFile(new File("D:\\test2.sql"), "r");
//		for (int i = 0; i < 3000; i++) {
//			System.out.println(raf.readLine());
//		}
//		
//		URI uri = new URI("");
//		String value = "2342s😂我34sdf";
//		System.out.println(value.length());
//		char[] charArray = value.toCharArray();
//		for (int i = 0; i < charArray.length; i++) {
//			System.out.println(charArray[i]);
//		}
		Random random = new Random();
		int value = (int)(random.nextDouble()*1000000000);
		System.out.println(value);
		System.out.println(new Random().nextInt(1000000000));
		System.out.println(new Random().nextDouble());
		System.out.println(Integer.MAX_VALUE);
		
	}
}
