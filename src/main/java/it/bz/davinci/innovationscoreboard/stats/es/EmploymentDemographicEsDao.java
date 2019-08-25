package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class EmploymentDemographicEsDao extends EsDao<EmploymentDemographicEs> {

    public EmploymentDemographicEsDao(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        super("employment-demographic", esClient, objectMapper);
    }

    public List<EmploymentDemographicEs> getEnterprisesWithInnovationActivitiesDividedByTerritory() {
        SearchRequest searchRequest = new SearchRequest(this.indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "POPI"))
                .filter(QueryBuilders.termQuery("ATECO_2007.keyword", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT.keyword", "W_GE10")));
        searchSourceBuilder.size(500);
        searchRequest.source(searchSourceBuilder);

        try {
            final SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            return parseSearchResponse(searchResponse);
        } catch (IOException e) {
            log.error("Failed to execute search", e);
            return Collections.emptyList();
        }
    }

    private List<EmploymentDemographicEs> parseSearchResponse(SearchResponse searchResponse) {
        final SearchHits hits = searchResponse.getHits();
        final List<EmploymentDemographicEs> result = new ArrayList<>();
        for (SearchHit hit : hits) {
            try {
                result.add(objectMapper.readValue(hit.getSourceAsString(), EmploymentDemographicEs.class));
            } catch (IOException e) {
                log.error("Failed to parse document", e);
            }
        }

        return result;
    }
}
