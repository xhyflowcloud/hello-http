package net.lovenn.http;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SimpleHttpService implements HttpService {

    private SocketChannel channel;

    private List<HttpHandler> handlers = new ArrayList<HttpHandler>();

    @Override
    public List<HttpHandler> getHttpHandler() {
        return this.handlers;
    }

    @Override
    public void addHttpHandler(HttpHandler httpHandler) {
        handlers.add(httpHandler);
    }

    @Override
    public void service(HttpRequest req, HttpResponse resp) {
        for (HttpHandler httpHandler: handlers) {
            httpHandler.handle(req, resp);
        }
    }

    @Override
    public SocketChannel channel() {
        return null;
    }

    public SimpleHttpService(SocketChannel channel) {
        this.channel = channel;
    }
}
