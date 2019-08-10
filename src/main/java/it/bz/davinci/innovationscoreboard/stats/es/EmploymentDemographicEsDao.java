package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

@Service
public class EmploymentDemographicEsDao extends EsDao<EmploymentDemographicEs> {
    public EmploymentDemographicEsDao(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        super("employment-demographic", esClient, objectMapper);
    }
}
