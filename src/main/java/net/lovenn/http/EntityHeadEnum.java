package net.lovenn.http;

public enum  EntityHeadEnum {

    ALLOW("Allow", "allow"),
    CONTENT_ENCODING("Content-Encoding", "content-encoding"),
    CONTENT_LANGUAGE("Content-Language", "content-language"),
    CONTENT_LENGTH("Content-Length", "content-length"),
    CONTENT_LOCATION("Content-Location", "content-location"),
    CONTENT_MD5("Content-MD5", "content-md5"),
    CONTENT_RANGE("Content-Range", "content-range"),
    CONTENT_TYPE("Content-Type", "content-type"),
    EXPIRES("Expires", "expires"),
    LAST_MODIFIED("Last-Modified", "last-modified"),
    ;

    private String field;

    private String definition;


    EntityHeadEnum(String field, String definition) {
        this.field = field;
        this.definition = definition;
    }

    public String getField() {
        return field;
    }

    public String getDefinition() {
        return definition;
    }
}
