package net.lovenn.http;

public enum HeadEnum {
    ;

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

    public enum  GeneralHeadEnum {

        CACHE_CONTROL("Cache-Control", "cache-control"),
        CONNECTION("Connection", "connection"),
        DATE("Date", "date"),
        PRAGMA("Pragma", "pragma"),
        TRAILER("Trailer", "trailer"),
        TRANSFER_ENCODING("Transfer-Encoding", "transfer-encoding"),
        UPGRADE("Upgrade", "upgrade"),
        VIA("Via", "via"),
        WARNING("Warning", "warning"),
        ;

        private String field;

        private String definition;


        GeneralHeadEnum(String field, String definition) {
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

    public enum  RequestHeadEnum {

        ACCEPT("Accept", "accept"),
        ACCEPT_CHARSET("Accept-Charset", "accept-charset"),
        ACCEPT_ENCODING("Accept-Encoding", "accept-encoding"),
        ACCEPT_LANGUAGE("Accept-Language", "accept-language"),
        AUTHORIZATION("Authorization", "authorization"),
        EXPECT("Expect", "expect"),
        FROM("From", "from"),
        HOST("Host", "host"),
        IF_MATCH("If-Match", "if-match"),
        IF_MODIFIED_SINCE("If-Modified-Since", "if-modified-since"),
        IF_NONE_MATCH("If-None-Match", "if-none-match"),
        IF_RANGE("If-Range", "if-range"),
        IF_UNMODIFIED_SINCE("If-Unmodified-Since", "if-unmodified-since"),
        MAX_FORWARDS("Max-Forwards", "max-forwards"),
        PROXY_AUTHORIZATION("Proxy-Authorization", "proxy-authorization"),
        RANGE("Range", "range"),
        REFERER("Referer", "referer"),
        TE("TE", "te"),
        USER_AGENT("User-Agent", "user-agent"),
        ;

        private String field;

        private String definition;


        RequestHeadEnum(String field, String definition) {
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

    public enum  ResponseHeadEnum {

        ACCEPT_RANGES("Accept-Ranges", "accept-ranges"),
        AGE("Age", "age"),
        ETAG("ETag", "etag"),
        LOCATION("Location", "location"),
        PROXY_AUTHENTICATE("Proxy-Authenticate", "proxy-authenticate"),
        TRANSFER_ENCODING("Transfer-Encoding", "transfer-encoding"),
        RETRY_AFTER("Retry-After", "retry-after"),
        VARY("Vary", "vary"),
        WWW_AUTHENTICATE("WWW-Authenticate", "www-authenticate"),
        ;

        private String field;

        private String definition;


        ResponseHeadEnum(String field, String definition) {
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
}
