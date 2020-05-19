package net.lovenn.http;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class Acceptor implements Runnable {

    final Selector selector;

    final ServerSocketChannel ssc;

    final ThreadPoolExecutor pool;

    final List<HttpHandler> handlers;


    public Acceptor(Selector selector, ServerSocketChannel ssc, final ThreadPoolExecutor pool, List<HttpHandler> handlers) {
        this.selector = selector;
        this.ssc = ssc;
        this.pool = pool;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        try {
            SocketChannel sc = ssc.accept();
            if(sc != null) {
                new Handler(selector, sc, pool, handlers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
