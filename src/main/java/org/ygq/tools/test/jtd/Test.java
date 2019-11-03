package org.ygq.tools.test.jtd;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) throws Exception {
		
		String file1 = "E:\\file1.txt";
		String file2 = "E:\\file2.txt";
		
		List<String> readAllLines1 = Files.readAllLines(Paths.get(file1));
		
		List<String> readAllLines2 = Files.readAllLines(Paths.get(file2));
		
		for (int i = 0; i < readAllLines2.size(); i++) {
			String value2 = readAllLines2.get(i);
			if (value2 == null || value2.trim().isEmpty()) {
				continue;
			}
			int end = value2.indexOf(" ");
			if (end == -1) {
				continue;
			}
			value2 = value2.substring(0, end);
			readAllLines2.set(i, value2);
		}
		
		for (int i = 0; i < readAllLines1.size(); i++) {
			String value1 = readAllLines1.get(i);
			if (value1 == null || value1.trim().isEmpty()) {
				readAllLines1.remove(i--);
				continue;
			}
			for (int j = 0; j < readAllLines2.size(); j++) {
				String value2 = readAllLines2.get(j);
				if (value2 == null || value2.trim().isEmpty()) {
					readAllLines2.remove(j--);
					continue;
				}
				if (value1.trim().contentEquals(value2.trim())) {
					readAllLines1.remove(i--);
					readAllLines2.remove(j--);
					break;
				}
			}
			
		}
		System.out.println(Arrays.toString(readAllLines1.toArray()));
		System.out.println(Arrays.toString(readAllLines2.toArray()));
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
//		long l = System.currentTimeMillis();
//		for (int i = 0; i < 100000; i++) {
//			LocalDateTime now = LocalDateTime.now();
//	        DateTimeFormatter pattern = 
//	                   DateTimeFormatter.ofPattern("G yyyy年MM月dd号 E a hh时mm分ss秒");
//	        String format = now.format(pattern);
////			SimpleDateFormat simpleDateFormat = 
////                    new SimpleDateFormat("G yyyy年MM月dd号 E a hh时mm分ss秒");
////			String format = simpleDateFormat.format(new Date());
//		}
	}
}
