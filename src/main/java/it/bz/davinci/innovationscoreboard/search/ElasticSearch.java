package it.bz.davinci.innovationscoreboard.search;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearch {

    private RestHighLevelClient client;

    public ElasticSearch() {

    }

    public void open() {
         client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("elasticsearch", 9200, "http")
                )
        );
    }

    public void close() throws IOException {
        client.close();
    }

    public void index() throws IOException {
        //if (! existsIndex("test")) {
            Map<String, Object> message = new HashMap<>();
            message.put("type", "text");
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", message);
            Map<String, Object> mapping = new HashMap<>();
            mapping.put("properties", properties);

            createIndex("test");
            addMapping("test", "doc", mapping);
        //}

        Map<String, Object> data = new HashMap<>();
        data.put("name", "Object 1");

        indexDocument("test", "doc", "1", data);
    }

    private boolean existsIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);

        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    private void createIndex(String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);

        client.indices().create(request, RequestOptions.DEFAULT);
    }

    private void addMapping(String index, String type, Map<String, Object> mapping) throws IOException {
        PutMappingRequest request = new PutMappingRequest(index);
        request.type(type);
        request.source(mapping);

        client.indices().putMapping(request, RequestOptions.DEFAULT);
    }

    private void indexDocument(String index, String type, String id, Map<String, Object> data) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index, type).id(id).source(data);

        client.index(indexRequest, RequestOptions.DEFAULT);
    }

}
