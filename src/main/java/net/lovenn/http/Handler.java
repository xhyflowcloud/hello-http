package net.lovenn.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Handler implements Runnable {

    final SelectionKey sk;

    final SocketChannel sc;

    static final int MAXIN = Short.MAX_VALUE, MAXOUT = Short.MAX_VALUE;

    final ByteBuffer INPUT = ByteBuffer.allocate(MAXIN);

    final ByteBuffer OUTPUT = ByteBuffer.allocate(MAXOUT);

    static final int READING = 0, SENDING = 1, PROCESSING = 3;

    volatile int state = READING;

    final ThreadPoolExecutor pool;

    List<HttpHandler> handlers;

    public Handler(Selector selector, SocketChannel sc, final ThreadPoolExecutor pool, List<HttpHandler> handlers) throws IOException {
        this.sc = sc;
        this.pool = pool;
        this.handlers = handlers;
        sc.configureBlocking(false);
        sk = sc.register(selector, SelectionKey.OP_READ, this);
        selector.wakeup();
    }

    @Override
    public void run() {
        if (state == READING) {
            read();
        } else if (state == SENDING) {
            send();
        }
    }

    void read() {
        try {
            if (inputIsComplete()) {
                process();
            }
        } catch (IOException e) {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    void send() {
        try {
            if (outputIsComplete()) {
                sc.close();
                OUTPUT.clear();
            }
        } catch (IOException e) {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private boolean inputIsComplete() throws IOException {
        sc.read(INPUT);
        return true;
    }

    private boolean outputIsComplete() throws IOException {
        OUTPUT.flip();
        sc.write(OUTPUT);
        return true;
    }

    private synchronized void process() {
        state = PROCESSING;
        pool.execute(new Processor());
    }

    synchronized void processHandOff() {
        state = SENDING;
        sk.interestOps(SelectionKey.OP_WRITE);
        sk.selector().wakeup();
    }

    public class Processor implements Runnable {

        @Override
        public void run() {
            SimpleHttpService simpleHttpService = new SimpleHttpService();
            simpleHttpService.addHttpHandler(handlers);
            HttpResponse response = new HttpResponse();
            simpleHttpService.service(HttpUtils.parseHttpRequest(INPUT), response);
            INPUT.clear();
            OUTPUT.put(HttpUtils.deparseHttpResponse(response));
            processHandOff();
        }
    }
}
