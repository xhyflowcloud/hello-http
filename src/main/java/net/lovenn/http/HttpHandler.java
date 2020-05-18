package net.lovenn.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class HttpHandler implements Runnable {

    private final SocketChannel channel;

    private final HttpRequest request;

    private final HttpResponse response;

    public HttpHandler(SocketChannel channel, HttpRequest request, HttpResponse response) {
        this.channel = channel;
        this.request = request;
        this.response = response;
    }

    public void run() {

    }
}
