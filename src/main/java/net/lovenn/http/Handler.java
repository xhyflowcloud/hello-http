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

    static final int MAXIN = 10000, MAXOUT = 10000;


    ByteBuffer output = ByteBuffer.allocate(MAXOUT);
    static final int READING = 0, SENDING = 1, PROCESSING = 3;
    int state = READING;

    static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10,
                                      0L,TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());

    public Handler(Selector selector, SocketChannel sc) throws IOException {
        this.sc = sc;
        sc.configureBlocking(false);
        sk = sc.register(selector, SelectionKey.OP_READ, this);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized void read() throws IOException {
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        sc.read(input);
        HttpRequest request = HttpUtils.parseHttpRequest(input);
        if (inputIsComplete()) {
            state = PROCESSING;
            pool.execute(new Processor(request, new HttpResponse()));
        }
    }

    synchronized void send() throws IOException {
        if (outputIsComplete()) {
            sk.cancel();
        }
    }

    private boolean inputIsComplete() {
        return true;
    }

    private boolean outputIsComplete() {
        return true;
    }

    private void process() { /* ... */ }

    synchronized void processHandOff() {
        state = SENDING; // or rebind attachment
        //sk.interestOps(SelectionKey.OP_WRITE);
    }

    public class Processor implements Runnable{
        private final HttpRequest request;

        private final HttpResponse response;


        public Processor(HttpRequest request, HttpResponse response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public void run() {
            SimpleHttpService simpleHttpService = new SimpleHttpService(sc);
            simpleHttpService.addHttpHandler(new SimpleHttpHandler(simpleHttpService));
            simpleHttpService.service(request, response);
            try {
                sc.write(HttpUtils.deparseHttpResponse(response));
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            processHandOff();

        }
    }


    class SimpleHttpHandler implements HttpHandler{

        private HttpService httpService;

        public SimpleHttpHandler(HttpService httpService) {
            this.httpService = httpService;
        }

        @Override
        public HttpService getHttpService() {
            return httpService;
        }

        @Override
        public void handle(HttpRequest request, HttpResponse response) {
            System.out.println(request);
            response.setHttpVersion(HttpVersionEnum.HTTP1_1.getValue());
            response.setStatusCode(HttpStatusCodeEnum.OK.getCode());
            response.setStatusCode(HttpStatusCodeEnum.OK.getDescription());
            response.write("Hello world");
        }
    }

}
