package net.lovenn.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

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

    public Map<String, String> getGeneralHeader() {
        return generalHeader;
    }

    public Map<String, String> getResponseHeader() {
        return responseHeader;
    }

    public Map<String, String> getEntityHeader() {
        return entityHeader;
    }

    public StringBuilder getMessageBody() {
        return messageBody;
    }
}
