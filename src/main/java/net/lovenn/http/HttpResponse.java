package net.lovenn.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String CR = "\r";
    private static final String LF = "\n";
    private static final String CRLF = "\r\n";
    private static final String SP = " ";
    private static final String LCS = "UTF-8";

    private String httpVersion;
    private String statusCode;
    private String reasonPhrase;
    private Map<String, String> generalHeader = new HashMap<String, String>();
    private Map<String, String> responseHeader = new HashMap<String, String>();
    private Map<String, String> entityHeader = new HashMap<String, String>();
    private StringBuilder messageBody = new StringBuilder();

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public void addGeneralHeader(String field, String value) {
        generalHeader.put(field, value);
    }

    public void addResponseHeader(String field, String value) {
        responseHeader.put(field, value);
    }

    public void addEntityHeader(String field, String value) {
        entityHeader.put(field, value);
    }

    public void write(String context) {
        messageBody.append(context);
    }

    public byte[] getResponse() {
        StringBuilder response = new StringBuilder();

        //设置 Status-Line
        response.append(httpVersion).append(SP);
        response.append(statusCode).append(SP);
        response.append(reasonPhrase).append(CRLF);

        //设置General Header
        if(generalHeader != null && !generalHeader.isEmpty()) {
            for (Map.Entry<String, String> general: generalHeader.entrySet()) {
                response.append(general.getKey()).append(":").append(general.getValue()).append(CRLF);
            }
        }
        //设置Response Header
        if(responseHeader != null && !responseHeader.isEmpty()) {
            for (Map.Entry<String, String> general: responseHeader.entrySet()) {
                response.append(general.getKey()).append(":").append(general.getValue()).append(CRLF);
            }
        }
        //设置Entity Header
        if(entityHeader != null && !entityHeader.isEmpty()) {
            for (Map.Entry<String, String> general: entityHeader.entrySet()) {
                response.append(general.getKey()).append(":").append(general.getValue()).append(CRLF);
            }
        }
        //设置空行
        response.append(CRLF);
        //设置MessageBody
        response.append(messageBody);
        try {
            return response.toString().getBytes(LCS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response.toString().getBytes();
    }
}
