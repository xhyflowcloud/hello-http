package net.lovenn.http;

import java.util.ArrayList;
import java.util.List;

public class SimpleHttpService implements HttpService {

    private List<HttpHandler> handlers = new ArrayList<HttpHandler>();

    @Override
    public List<HttpHandler> getHttpHandler() {
        return this.handlers;
    }

    @Override
    public void addHttpHandler(List<HttpHandler> httpHandlers) {
        handlers.addAll(httpHandlers);
    }

    @Override
    public void service(HttpRequest req, HttpResponse resp) {
        for (HttpHandler httpHandler : handlers) {
            httpHandler.handle(req, resp);
        }
    }
}
