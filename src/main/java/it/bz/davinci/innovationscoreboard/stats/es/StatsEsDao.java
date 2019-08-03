package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.davinci.innovationscoreboard.stats.Stats;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class StatsEsDao {

    private final String INDEX = "stats";


    private final RestHighLevelClient esClient;
    private ObjectMapper objectMapper;


    @Autowired
    public StatsEsDao(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        this.esClient = esClient;
        this.objectMapper = objectMapper;
    }

    public void index(EsStats stats) {

        Map<String, Object> map = objectMapper.convertValue(stats, Map.class);

        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source(map);

        try {
            esClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO properly log exception
        }
    }

}
