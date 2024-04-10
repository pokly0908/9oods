package com.kuku9.goods.global.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import javax.net.ssl.SSLContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ElasticConfig{

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private int port;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password = "cZ67qv8Yw9rCsi26G_6V";
    @Value("${elasticsearch.encodedApiKey}")
    private String encodedApiKey;
    @Value("${elasticsearch.fingerprint}")
    private String fingerPrint;

    public ElasticsearchClient elasticsearchClient() {
        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerPrint);

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
            AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        try {
            RestClient restClient = RestClient
                .builder(new HttpHost(host, port, "https"))
                .setDefaultHeaders(new Header[]{
                    new BasicHeader("Authorization", "ApiKey" + encodedApiKey)
                })
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder
                    .setSSLContext(sslContext)
                    .setDefaultCredentialsProvider(credsProv))
                .build();

            ElasticsearchTransport transport =
                new RestClientTransport(restClient, new JacksonJsonpMapper());

            ElasticsearchClient elasticsearchClient = new ElasticsearchClient(transport);

            return elasticsearchClient;
        } catch (Exception e) {
            log.error("Elasticsearch Rest Client Error", e);
            return null;
        }
    }
}
