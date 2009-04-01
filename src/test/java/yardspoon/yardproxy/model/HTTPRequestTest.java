package yardspoon.yardproxy.model;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Assert;
import org.junit.Test;

public class HTTPRequestTest {

	private static final CharsetDecoder DECODER = Charset.forName("UTF-8").newDecoder();

	@Test
	public void encodeSimpleGETRequest() throws Exception {
		String expected = "GET http://www.google.com/ HTTP/1.1\r\n";
		HTTPRequest request = HTTPRequest.GET("http://www.google.com/");
		
		IoBuffer buffer = request.encode();
		
		Assert.assertEquals(expected, buffer.getString(DECODER));
	}
	
	@Test
	public void encodeGETWithSingleHeader() throws Exception {
		String expected = "GET http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\n";
		HTTPRequest request = HTTPRequest.GET("http://www.google.com/");
		request.addHeader("Host", "google.com");
		
		IoBuffer buffer = request.encode();
		
		Assert.assertEquals(expected, buffer.getString(DECODER));
	}
	
	@Test
	public void encodeGETWithMultipleHeaders() throws Exception {
		String expected = "GET http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\nContent-Length: 35\r\nUser-Agent: Mozilla/5.0 (Windows)\r\n";
		HTTPRequest request = HTTPRequest.GET("http://www.google.com/");
		request.addHeader("Host", "google.com");
		request.addHeader("Content-Length", "35");
		request.addHeader("User-Agent", " Mozilla/5.0 (Windows) ");
		
		IoBuffer buffer = request.encode();
		
		Assert.assertEquals(expected, buffer.getString(DECODER));
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
		
		Assert.assertEquals(expected, buffer.getString(DECODER));
	}
	
	@Test
	public void encodePOSTWithHeadersWithoutMessageBody() throws Exception {
		String expected = "POST http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\nContent-Length: 35\r\nUser-Agent: Mozilla/5.0 (Windows)\r\n\r\n";
		HTTPRequest request = HTTPRequest.POST("http://www.google.com/");
		request.addHeader("Host", "google.com");
		request.addHeader("Content-Length", "35");
		request.addHeader("User-Agent", " Mozilla/5.0 (Windows) ");
		
		IoBuffer buffer = request.encode();
		
		Assert.assertEquals(expected, buffer.getString(DECODER));
	}
	
}
