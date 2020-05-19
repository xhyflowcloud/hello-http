package net.lovenn.http;

public enum HttpVersionEnum {
    HTTP1_1("HTTP/1.1")
    ;
    private String value;

    HttpVersionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
