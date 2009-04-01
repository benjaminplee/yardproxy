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
		String expected = "GET http://www.google.com/ HTTP/1.1\r\n\r\n";
		HTTPRequest request = HTTPRequest.GET("http://www.google.com/");
		
		IoBuffer buffer = request.encode();
		
		Assert.assertEquals(expected, buffer.getString(DECODER));
	}
	
	@Test
	public void encodeGETWithSingleHeader() throws Exception {
		String expected = "GET http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\n\r\n";
		HTTPRequest request = HTTPRequest.GET("http://www.google.com/");
		request.addHeader("Host", "google.com");
		
		IoBuffer buffer = request.encode();
		
		Assert.assertEquals(expected, buffer.getString(DECODER));
	}
	
	@Test
	public void encodeGETWithMultipleHeaders() throws Exception {
		String expected = "GET http://www.google.com/ HTTP/1.1\r\nHost: google.com\r\nContent-Length: 35\r\nUser-Agent: Mozilla/5.0 (Windows)\r\n\r\n";
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
	
	@Test
	public void decodeFromValidGETBuffer() {
		Assert.fail("NOT DONE YET");
	}
	
	// TODO decode extended get
	
	// TODO decode post w/o body
	
	// TODO decode post w/ body
	
	// TODO empty buffer is not ready to decode
	
	// TODO incomplete request line not ready
	
	// TODO complete get line w/o headers ready
	
	// TODO get line w/ incomplete headers not ready
	
	// TODO complete get line w/ headers ready
	
	// TODO post w/o headers incomplete
	
	// TODO post w/ correct headers but not body not ready
	
	// TODO post w/ correct headers and body ready
	
}
