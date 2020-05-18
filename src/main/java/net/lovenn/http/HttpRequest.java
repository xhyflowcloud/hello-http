package net.lovenn.http;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String SP = " ";
    public static final String LCS = "UTF-8";

    private String method;
    private String requestURI;
    private String httpVersion;
    private Map<String, String> generalHeader = new HashMap<String, String>();
    private Map<String, String> requestHeader = new HashMap<String, String>();
    private Map<String, String> entityHeader = new HashMap<String, String>();
    private byte[] messageBody;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void addGeneralHeader(String field, String value) {
        generalHeader.put(field, value);
    }

    public void addRequestHeader(String field, String value) {
        requestHeader.put(field, value);
    }

    public void addEntityHeader(String field, String value) {
        entityHeader.put(field, value);
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }
}
