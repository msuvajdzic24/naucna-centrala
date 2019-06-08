package ftn.sep.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;

@Configuration
public class RestClientConfiguration {

    private String allPassword = "bar5slova";

    @Bean
    public HttpComponentsClientHttpRequestFactory restTemplate(RestTemplateBuilder builder) throws Exception {

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(ResourceUtils.getFile("classpath:keystore.p12"), allPassword.toCharArray(), allPassword.toCharArray()).loadTrustMaterial(ResourceUtils.getFile("classpath:truststore.jks"), allPassword.toCharArray())
                .build();

        HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        HttpComponentsClientHttpRequestFactory hcschrf = new HttpComponentsClientHttpRequestFactory();
        hcschrf.setHttpClient(client);

        return hcschrf;
    }
}

