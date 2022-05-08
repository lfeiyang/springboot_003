package com.sy.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [一句话注释]
 *
 * @author lfeiyang
 * @since 2022-05-08 23:49
 */
@Configuration
public class EsConfig {
    @Value("${elasticsearch.host:192.168.58.100}")
    public String host;

    /**
     * 之前使用transport的接口的时候是9300端口，现在使用HighLevelClient则是9200端口
     */
    @Value("${elasticsearch.port:9200}")
    public int port;

    public static final String SCHEME = "http";

    @Value("${elasticsearch.username:elastic}")
    public String username;

    @Value("${elasticsearch.authenticationPassword:sa123456!@#}")
    public String authenticationPassword;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, authenticationPassword));

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, SCHEME)).
                setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }
}
