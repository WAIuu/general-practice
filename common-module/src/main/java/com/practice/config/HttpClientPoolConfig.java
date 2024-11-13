package com.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "http.client")
public class HttpClientPoolConfig {
    private static final int DEFAULT_MAX_TOTAL_CONNECT = 100;
    private static final int DEFAULT_MAX_CONNECT_PER_ROUTE = 100;
    private static final int DEFAULT_CONNECT_TIMEOUT = 20000;
    private static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 2000;

    /**
     * 最大连接数
     */
    private Integer maxTotalConnect = DEFAULT_MAX_TOTAL_CONNECT;
    /**
     * 单路由量大连接数
     */
    private Integer maxConnectPerRoute = DEFAULT_MAX_CONNECT_PER_ROUTE;
    /**
     * 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
     */
    private Integer connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    /**
     * 服务器返回数据(response)的时间，超过该时间抛出read timeout
     */
    private Integer socketTimeout = DEFAULT_CONNECT_TIMEOUT;
    /**
     *从连接池中获取连接的超时时间
     */
    private Integer connectionRequestTimeout = DEFAULT_CONNECT_REQUEST_TIMEOUT;
}