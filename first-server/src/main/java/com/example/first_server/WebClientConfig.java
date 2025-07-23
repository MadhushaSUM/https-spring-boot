package com.example.first_server;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

@Configuration
public class WebClientConfig {
    @Value("${gateway.ssl.key-store}")
    private Resource keyStore;

    @Value("${gateway.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${gateway.ssl.trust-store}")
    private Resource trustStore;

    @Value("${gateway.ssl.trust-store-password}")
    private String trustStorePassword;


    @Bean
    public WebClient getWebClient() throws Exception {

        // Create SSL context
        SslContext sslContext = SslContextBuilder.forClient()
                .keyManager(getKeyManagerFactory())
                .trustManager(getTrustManagerFactory())
                .build();

        HttpClient httpClient = HttpClient.create()
                .secure(ssl -> ssl.sslContext(sslContext));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private KeyManagerFactory getKeyManagerFactory() throws Exception {
        KeyStore keyStoreObj = KeyStore.getInstance("PKCS12");
        keyStoreObj.load(this.keyStore.getInputStream(), keyStorePassword.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStoreObj, keyStorePassword.toCharArray());

        return kmf;
    }

    private TrustManagerFactory getTrustManagerFactory() throws Exception {
        KeyStore trustStoreObj = KeyStore.getInstance("PKCS12");
        trustStoreObj.load(this.trustStore.getInputStream(), trustStorePassword.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStoreObj);

        return tmf;
    }
}
