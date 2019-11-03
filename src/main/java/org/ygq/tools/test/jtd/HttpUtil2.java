package org.ygq.tools.test.jtd;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class HttpUtil2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		String uri = "http://piaowu.jxzl.cc:8901/et/templates/upload/saleTicketImage/20190502/F3D61E10DE364C048910237655A04CF8.jpg";
		
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();
		
		HttpResponse<byte[]> response = client.send(request, BodyHandlers.ofByteArray());

		System.out.println(response.body());
	}
}
