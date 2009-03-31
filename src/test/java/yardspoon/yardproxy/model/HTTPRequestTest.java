package yardspoon.yardproxy.model;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Assert;
import org.junit.Test;

public class HTTPRequestTest {

	@Test
	public void encodeSimpleGETRequest() {
		String expected = "GET http://www.google.com/ HTTP/1.1\r\n";
		HTTPRequest request = HTTPRequest.GET("http://www.google.com/");
		
		IoBuffer buffer = request.encode();
		
		Assert.assertArrayEquals(expected.getBytes(), buffer.array());
	}
	
	@Test
	public void encodeGETWithSingleHeader() {
		String expected = "GET http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\n";
		HTTPRequest request = HTTPRequest.GET("http://www.google.com/");
		request.addHeader("Host", "google.com");
		
		IoBuffer buffer = request.encode();
		
		Assert.assertArrayEquals(expected.getBytes(), buffer.array());
	}
	
	@Test
	public void encodeGETWithMultipleHeaders() {
		String expected = "GET http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\nContent-Length: 35\r\nUser-Agent: Mozilla/5.0 (Windows)\r\n";
		HTTPRequest request = HTTPRequest.GET("http://www.google.com/");
		request.addHeader("Host", "google.com");
		request.addHeader("Content-Length", "35");
		request.addHeader("User-Agent", " Mozilla/5.0 (Windows) ");
		
		
		IoBuffer buffer = request.encode();
		
		Assert.assertArrayEquals(expected.getBytes(), buffer.array());
	}
	
	@Test
	public void encodePOSTWithHeadersAndMessageBody() throws Exception {
		String expected = "POST http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\nContent-Length: 35\r\nUser-Agent: Mozilla/5.0 (Windows)\r\n\r\nCONTENT";
		HTTPRequest request = HTTPRequest.POST("http://www.google.com/");
		request.setMessageBody("CONTENT");
		request.addHeader("Host", "google.com");
		request.addHeader("Content-Length", "35");
		request.addHeader("User-Agent", " Mozilla/5.0 (Windows) ");
		
		IoBuffer buffer = request.encode();
		
		System.out.println("***");
		System.out.println(buffer.getString(Charset.forName( "UTF-8" ).newDecoder()));
		System.out.println("***");
		System.out.println(expected);
		System.out.println("***");
		
		Assert.assertArrayEquals(expected.getBytes(), buffer.array());
	}
	
	@Test
	public void encodePOSTWithHeadersWithoutMessageBody() throws Exception {
		String expected = "POST http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\nContent-Length: 35\r\nUser-Agent: Mozilla/5.0 (Windows)\r\n\r\n";
		HTTPRequest request = HTTPRequest.POST("http://www.google.com/");
		request.addHeader("Host", "google.com");
		request.addHeader("Content-Length", "35");
		request.addHeader("User-Agent", " Mozilla/5.0 (Windows) ");
		
		IoBuffer buffer = request.encode();
		
		System.out.println("***");
		System.out.println(buffer.getString(Charset.forName( "UTF-8" ).newDecoder()));
		System.out.println("***");
		System.out.println(expected);
		System.out.println("***");
		
		Assert.assertArrayEquals(expected.getBytes(), buffer.array());
	}
	
}
