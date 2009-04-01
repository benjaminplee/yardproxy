package yardspoon.yardproxy.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

public class HTTPRequest {
	private enum HTTPMethod {
		POST, GET;
	}
	
	private final HTTPMethod method;
	private final String absoluteURI;
	private final Map<String, String> headers;
	private String messageBody = "";

	private HTTPRequest(HTTPMethod method, String absoluteURI) {
		this.method = method;
		this.absoluteURI = absoluteURI;
		headers = new LinkedHashMap<String, String>();
	}

	public IoBuffer encode() {
		IoBuffer buffer = IoBuffer.allocate(0, false);
		buffer.clear();
		
		addStringBytesAndCRLF(buffer, method.name() + " " + absoluteURI + " HTTP/1.1");
		
		for (String fieldName : headers.keySet()) {
			addStringBytesAndCRLF(buffer, fieldName + ": " + headers.get(fieldName).trim());
		}
		
		if(method == HTTPMethod.POST) {
			addCRLF(buffer);
			addStringBytes(buffer, messageBody);
		}
		
		buffer.flip();
		
		return buffer;
	}

	public void addHeader(String fieldName, String fieldValue) {
		headers.put(fieldName, fieldValue);
	}

	public static HTTPRequest GET(String absoluteURI) {
		return new HTTPRequest(HTTPMethod.GET, absoluteURI);
	}

	public static HTTPRequest POST(String absoluteURI) {
		return new HTTPRequest(HTTPMethod.POST, absoluteURI);
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	private void addStringBytesAndCRLF(IoBuffer buffer, String line) {
		addStringBytes(buffer, line);
		addCRLF(buffer);
	}

	private void addCRLF(IoBuffer buffer) {
		addStringBytes(buffer, "\r\n");
	}

	private void addStringBytes(IoBuffer buffer, String line) {
		byte[] bytes = line.getBytes();
		buffer.expand(bytes.length);
		buffer.put(bytes);
	}

}
