package net.lovenn.http;

import java.nio.channels.SocketChannel;
import java.util.List;

public interface HttpService {

    List<HttpHandler> getHttpHandler();

    void addHttpHandler(HttpHandler httpHandler);

    void service(HttpRequest req, HttpResponse resp);

    SocketChannel channel();
}
