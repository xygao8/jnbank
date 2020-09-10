package com.iflytek.bank.utils;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author: xiewei2
 * @version: 2.0
 * @since: 2018/7/27 13:43
 * @Description:
 */
public class HttpConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnectionManager.class);
    /**
     * 最大连接数增加
     */
    private static final int MAX_CONNECTION_SIZE = 200;
    /**
     * 每个路由基础的连接数
     */
    private static final int MAX_PER_ROUTE = 50;

    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    private HttpConnectionManager(boolean isTrust) {
        try {
            SSLContext sslContext;
            if(isTrust) {
                sslContext = getSSLContextTrustAll();
            } else {
                sslContext = getSSLContextCertificate();
            }
            init(sslContext);
        } catch (Exception e) {
            logger.error("初始化http连接池失败: ", e);
        }
    }

    private static final class HttpConnectionManagerHolder {
        private static HttpConnectionManager httpConnectionManager = new HttpConnectionManager(true);
    }

    private static final class HttpsConnectionManagerHolder {
        private static HttpConnectionManager httpConnectionManager = new HttpConnectionManager(false);
    }


    public static HttpConnectionManager getHttpClientFactory() {
        return HttpConnectionManagerHolder.httpConnectionManager;
    }

    public static HttpConnectionManager getHttpsClientFactory() {
        return HttpsConnectionManagerHolder.httpConnectionManager;
    }

    private void init(SSLContext sslContext) {
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslConnectionSocketFactory)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        poolingHttpClientConnectionManager.setMaxTotal(MAX_CONNECTION_SIZE);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
    }

    CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setKeepAliveStrategy(connectionKeepAliveStrategy)
                .build();
    }


    private SSLContext getSSLContextCertificate() throws Exception {
        // 获得密匙库
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (FileInputStream inputStream = new FileInputStream(
                new File("D:\\idea_workspace\\learning\\src\\main\\resources\\https\\keystore.p12"))) {
            // 密匙库的密码
            trustStore.load(inputStream, "iflytek".toCharArray());
        }
        return SSLContexts.custom().loadTrustMaterial(trustStore, TrustSelfSignedStrategy.INSTANCE)
                .build();
    }


    private SSLContext getSSLContextTrustAll() throws Exception {
        // 信任所有
        return new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
    }


    /**
     * DefaultConnectionKeepAliveStrategy 默认实现
     */
    private ConnectionKeepAliveStrategy connectionKeepAliveStrategy = (response, context) -> {
        final HeaderElementIterator it = new BasicHeaderElementIterator(
                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
        while (it.hasNext()) {
            final HeaderElement headerElement = it.nextElement();
            final String param = headerElement.getName();
            final String value = headerElement.getValue();
            if (value != null && "timeout".equalsIgnoreCase(param)) {
                try {
                    return Long.parseLong(value) * 1000;
                } catch (final NumberFormatException ignore) {
                    return 1;
                }
            }
        }
        return 1;
    };
}
