package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
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
public class ResearchAndDevelopmentEsDao extends EsDao<ResearchAndDevelopmentEs> {
    public ResearchAndDevelopmentEsDao(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        super("research-and-development", esClient, objectMapper);
    }

    public List<ResearchAndDevelopmentEs> getResearchAndDevelopmentPersonnelInHouseDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "RDPHIM"))
                .filter(QueryBuilders.termQuery("CORSO_LAUREA.keyword", "ALL"));

        return searchByQuery(filter);
    }

    private List<ResearchAndDevelopmentEs> searchByQuery(BoolQueryBuilder query) {
        SearchRequest searchRequest = new SearchRequest(this.indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchRequest.source(searchSourceBuilder);

        try {
            final SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            return parseSearchResponse(searchResponse);
        } catch (IOException e) {
            log.error("Failed to execute search", e);
            return Collections.emptyList();
        }
    }

    private List<ResearchAndDevelopmentEs> parseSearchResponse(SearchResponse searchResponse) {
        final SearchHits hits = searchResponse.getHits();
        final List<ResearchAndDevelopmentEs> result = new ArrayList<>();
        for (SearchHit hit : hits) {
            try {
                result.add(objectMapper.readValue(hit.getSourceAsString(), ResearchAndDevelopmentEs.class));
            } catch (IOException e) {
                log.error("Failed to parse document", e);
            }
        }

        return result;
    }
}
