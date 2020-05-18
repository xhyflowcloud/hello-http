package net.lovenn.http;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
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
                // 接收到连接请求时
                if (selectionKey.isAcceptable()) {
                    handleAccept();
                }
                // 读数据
                if (selectionKey.isReadable()) {
                    handleRead();
                }
                iterator.remove();
            }
        }
    }

    public class HttpHandler {

        private int bfs = 1024;

        private String lcs = "UTF-8";

        private final SelectionKey key;

        public HttpHandler(SelectionKey key) {
            this.key = key;
        }

        public void convert() {
            try {
                // 接收到连接请求时
                if (key.isAcceptable()) {
                    handleAccept();
                }
                // 读数据
                if (key.isReadable()) {
                    handleRead();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        private void handleAccept() throws IOException {
            SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
            if (sc == null) {
                return;
            }

            sc.configureBlocking(false);
            sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bfs));
        }

        private void handleRead() throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();

            ByteBuffer bf = (ByteBuffer) key.attachment();

            bf.clear();

            if (sc.read(bf) < 0) {
                sc.close();
            } else {
                HttpRequest request = HttpUtils.parseHttpRequest(bf.duplicate());

                // 返回客户端
//            StringBuilder sendString = new StringBuilder();
//            sendString.append("HTTP/1.1 200 OK\r\n");//响应报文首行，200表示处理成功
//            sendString.append("Content-Type:text/html;charset=" + lcs + "\r\n");
//            sendString.append("\r\n");// 报文头结束后加一个空行
//
//            sendString.append("<!DOCTYPE html>");
//            sendString.append("<html lang=\"en\">");
//            sendString.append("<head>");
//            sendString.append("    <meta charset=\"UTF-8\">");
//            sendString.append("    <title>Title</title>");
//            sendString.append("</head>");
//            sendString.append("<body>");
//            sendString.append("    <h4>hello world! </h4>");
//            sendString.append("</body>");
//            sendString.append("</html>");

//            HttpResponse response = new HttpResponse();
//            response.setHttpVersion("HTTP/1.1");
//            response.setStatusCode("200");
//            response.setReasonPhrase("OK");
//            response.addResponseHeader("Content-Type", "text/html;charset=" + lcs);
//            response.write("<!DOCTYPE html>");
//            response.write("<html lang=\"en\">");
//            response.write("<head>");
//            response.write("    <meta charset=\"UTF-8\">");
//            response.write("    <title>Title</title>");
//            response.write("</head>");
//            response.write("<body>");
//            response.write("    <h4>hello world! </h4>");
//            response.write("</body>");
//            response.write("</html>");
            }

        }
    }
}
