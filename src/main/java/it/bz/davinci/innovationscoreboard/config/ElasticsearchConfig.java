package it.bz.davinci.innovationscoreboard.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.scheme}")
    private String scheme;

    @Value("${elasticsearch.user}")
    private String user;

    @Value("${elasticsearch.password}")
    private String password;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(user, password));

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, port, scheme)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
        );

        return client;
    }

    // We need to expose the underlying rest client as well in order to support the actuator health check
    // https://github.com/spring-projects/spring-boot/issues/16049
    @Bean
    public RestClient esRestClient() {
        return client().getLowLevelClient();
    }

}
