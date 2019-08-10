package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

@Service
public class InnovationEsDao extends EsDao<InnovationEs> {

    public InnovationEsDao(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        super("innovation", esClient, objectMapper);
    }

}
