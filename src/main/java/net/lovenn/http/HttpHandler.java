package net.lovenn.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class HttpHandler implements Runnable {

    private int bfs = 1024;

    private String lcs = "UTF-8";

    private final SelectionKey key;

    public HttpHandler(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {
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
        if(sc == null){ return;}

        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bfs));
    }

    private void handleRead() throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();

        ByteBuffer bf = (ByteBuffer) key.attachment();

        bf.clear();

        if(sc.read(bf) < 0) {
            sc.close();
        } else {
            HttpRequest request = HttpUtils.parseHttpRequest(bf.duplicate());
            System.out.println(request);
            bf.flip();
//            byte[] ba = new byte[1024];
//            bf.get(ba, 0, bf.limit());
//            System.out.println(new String(ba, Charset.forName("US-ASCII")));

            String receivedString = Charset.forName(lcs).newDecoder().decode(bf).toString();
            String[] requestMessage = receivedString.split("\r\n");
            for (String s: requestMessage) {
                System.out.println(s);
                if(s.isEmpty()) {
                    break;
                }
            }
            // 控制台打印首行信息
            String[] firstLine = requestMessage[0].split(" ");
            System.out.println();
            System.out.println("Method:\t" + firstLine[0]);
            System.out.println("url:\t" + firstLine[1]);
            System.out.println("HTTP Version:\t" + firstLine[2]);
            System.out.println();
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

            HttpResponse response = new HttpResponse();
            response.setHttpVersion("HTTP/1.1");
            response.setStatusCode("200");
            response.setReasonPhrase("OK");
            //response.addResponseHeader("Content-Type", "text/plain;charset=" + lcs);
            response.addResponseHeader("Access-Control-Allow-Origin", "*");
            //response.addResponseHeader("Allow","OPTIONS, GET, HEAD, POST");
            response.addResponseHeader("Access-Control-Allow-Methods", "OPTIONS, GET, HEAD, POST");
            response.addResponseHeader("Access-Control-Allow-Headers", "Content-Type");
            //response.addResponseHeader("Cache-Control", "max-age=604800");
            //response.addResponseHeader("Content-Length", "0");
            response.addResponseHeader("Content-Type", "application/json;");
            //response.addResponseHeader("Content-Location", "/a");
            //response.write("{\"a\":\"b\"}");

            bf = ByteBuffer.wrap(response.getResponse());
            sc.write(bf);
            sc.close();
        }

    }
}
