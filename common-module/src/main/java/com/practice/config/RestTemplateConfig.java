package com.practice.config;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(HttpClientPoolConfig.class)
public class RestTemplateConfig {

    @Autowired
    private HttpClientPoolConfig poolConfig;

    @Bean
    public RestTemplate restTemplate() {
        // 注册HTTP和HTTPS协议的连接套接字工厂
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        // 创建连接池管理器
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(200); // 最大连接数
        connectionManager.setDefaultMaxPerRoute(20); // 每个路由最大连接数
        // 配置认证机制
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("username", "password"));
        // 配置拦截器
//        HttpRequestInterceptor httpRequestInterceptor = new HttpRequestInterceptor() {
//            @Override
//            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
//                System.out.println("正在发送请求：" + request.getRequestLine());
//            }
//        };
//        HttpResponseInterceptor httpResponseInterceptor = new HttpResponseInterceptor() {
//            @Override
//            public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
//                System.out.println("接收到响应：" + response.getStatusLine());
//            }
//        };
        // 配置请求连接参数
        RequestConfig requestConfig = RequestConfig.custom()
                // 服务器返回数据(response)的时间，超过该时间抛出read timeout
                .setSocketTimeout(poolConfig.getSocketTimeout())
                // 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
                .setConnectTimeout(poolConfig.getConnectTimeout())
                // 从连接池中获取连接的超时时间，超过该时间未拿到可用连接，
                // 会抛出org.apache.http.conn.ConnectionPoolTimeoutException:
                .setConnectionRequestTimeout(poolConfig.getConnectionRequestTimeout())
                .build();
        // 设置代理
        HttpHost proxy = new HttpHost("proxy.example.com", 8080);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
//                .setDefaultCredentialsProvider(credentialsProvider)
                .setConnectionManager(connectionManager)
                .setProxy(proxy)
//                .addInterceptorFirst(httpRequestInterceptor)
//                .addInterceptorFirst(httpResponseInterceptor)
                ;
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build()));
    }
}
