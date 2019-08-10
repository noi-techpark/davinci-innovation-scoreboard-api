package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

@Service
public class ResearchAndDevelopmentEsDao extends EsDao<ResearchAndDevelopmentEs> {
    public ResearchAndDevelopmentEsDao(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        super("research-and-development", esClient, objectMapper);
    }
}
