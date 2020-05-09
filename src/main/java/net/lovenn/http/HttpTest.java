package net.lovenn.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpTest {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/");
        HttpOptions post = new HttpOptions("http://localhost:8080/");
        post.setHeader("Origin", "http://localhost:63342");
        post.setHeader("Pragma", "no-cache");
        post.setHeader("Cache-Control", "no-cache");
        post.setHeader("Accept", "*/*");
        post.setHeader("Access-Control-Request-Method", "POST");
        post.setHeader("Access-Control-Request-Headers", "content-type");
        post.setHeader("Sec-Fetch-Mode", "cors");
        post.setHeader("Sec-Fetch-Site", "same-site");
        post.setHeader("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8");
        CloseableHttpResponse response = httpClient.execute(post);
        System.out.println(response);
    }
}
