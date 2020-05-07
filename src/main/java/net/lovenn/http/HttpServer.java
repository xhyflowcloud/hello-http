package net.lovenn.http;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.concurrent.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {

        ThreadFactory nameFactory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-thread%d")
                .build();
        ExecutorService pool = new ThreadPoolExecutor(10, 20, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100), nameFactory, new ThreadPoolExecutor.AbortPolicy());

        ServerSocketChannel ssc = ServerSocketChannel.open();

        ssc.socket().bind(new InetSocketAddress(8080));

        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //pool.execute(new HttpHandler(selectionKey));
                new HttpHandler(selectionKey).run();
                iterator.remove();
            }
        }
    }
}