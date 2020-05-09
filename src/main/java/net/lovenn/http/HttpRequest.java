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
    private Map<String, String> generalHeader = new HashMap<>();
    private Map<String, String> responseHeader = new HashMap<>();
    private Map<String, String> entityHeader = new HashMap<>();
    private StringBuilder messageBody = new StringBuilder();

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

    public void parse(ByteBuffer buffer) throws CharacterCodingException {
        buffer.flip();
        String receivedString = Charset.forName(LCS).newDecoder().decode(buffer).toString();
        String[] requestMessage = receivedString.split(CRLF);
        String[] requestLine = requestMessage[0].split(SP);
        this.setMethod(requestLine[0]);
        this.setRequestURI(requestLine[1]);
        this.setHttpVersion(requestLine[2]);


    }
}
