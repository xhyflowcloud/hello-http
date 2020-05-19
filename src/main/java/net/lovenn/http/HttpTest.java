package net.lovenn.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class HttpTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpServer httpServer = new HttpServer();
        httpServer.addHttpHandler(new SimpleHttpHandler());
        httpServer.run();
    }
}
