package org.ygq.tools.test.jtd;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class HttpConnectionTest {

	
	public static void main(String[] args) throws URISyntaxException {
		String url = (args == null || args.length ==0) ? "http://piaowu.jxzl.cc:8901/et/templates/upload/saleTicketImage/20190502/F3D61E10DE364C048910237655A04CF8.jpg" : args[0];
		URL resource = Thread.currentThread().getContextClassLoader().getResource(".");
		Path path = Paths.get(resource.toURI());
		int value = (int)(new Random().nextDouble() * 1000000);
		path = path.resolve("xia_zai_"+value+".jpg");
		HttpUtil.getGetSource(url, null, null, path);
	}
}
