package com.sy.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

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

    @Value("${elasticsearch.password:sa123456!@#}")
    public String authenticationPassword;

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // 凭证提供者
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        // 这块是elasticsearch的账户和密码（elastic）
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, authenticationPassword));

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, SCHEME)).
                setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(restHighLevelClient());
    }
}
