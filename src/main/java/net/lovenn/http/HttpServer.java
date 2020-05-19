package net.lovenn.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpServer implements Runnable{

    private int port = 8080;

    private int minSpare = 10;

    private int maxConnection = 100;

    private List<HttpHandler> handlers = new ArrayList<>();

    private CountDownLatch count = new CountDownLatch(1);

    @Override
    public void run() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(minSpare, maxConnection,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        if(handlers.isEmpty()) {
            System.out.println("HttpServer ERROR, Please add handler");
        }
        try {
            new Thread(new Reactor(port, pool, handlers)).start();
        } catch (IOException e) {
            System.out.println("HttpServer ERROR, " + e.getMessage());
        }
        System.out.println("HttpServer Start, port:" + port);

        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addHttpHandler(HttpHandler handler) {
        if(handler == null) {
            return;
        }
        handlers.add(handler);
    }
}
