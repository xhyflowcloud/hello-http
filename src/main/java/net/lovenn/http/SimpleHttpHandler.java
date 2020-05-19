package net.lovenn.http;

public class SimpleHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
       // System.out.println(request);
        response.setHttpVersion(HttpVersionEnum.HTTP1_1.getValue());
        response.setStatusCode(HttpStatusCodeEnum.OK.getCode());
        response.setReasonPhrase(HttpStatusCodeEnum.OK.getDescription());
        response.write("Hello World");
    }
}
