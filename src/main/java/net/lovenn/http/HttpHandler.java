package net.lovenn.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public interface HttpHandler {

    HttpService getHttpService();

    void handle(HttpRequest request, HttpResponse response);
}
