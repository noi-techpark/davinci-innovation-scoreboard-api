package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.management.Query;
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

        return scrollByQuery(filter);
    }

    public List<ResearchAndDevelopmentEs> getDomesticResearchAndDevelopmentExpenditureInHouseDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "DRDEIM"))
                .filter(QueryBuilders.termQuery("ATTIVEC_CSC.keyword", "ALL"))
                .filter(QueryBuilders.termQuery("SETTISTSEC2010_B.keyword", "S1"))
                .filter(QueryBuilders.termQuery("TIPENTSPE.keyword", "99"))
                .filter(QueryBuilders.termQuery("SEXISTAT1.keyword", "9"))
                .filter(QueryBuilders.termQuery("TITOLO_STUDIO.keyword", "99"))
                .filter(QueryBuilders.termQuery("CORSO_LAUREA.keyword", "ALL"))
                .filter(QueryBuilders.termQuery("flagCodes.keyword", ""));

        return scrollByQuery(filter);
    }

    private List<ResearchAndDevelopmentEs> searchByQuery(BoolQueryBuilder query) {
        SearchRequest searchRequest = new SearchRequest(this.indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.size(1000);
        searchRequest.source(searchSourceBuilder);

        try {
            final SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            return parseSearchResponse(searchResponse);
        } catch (IOException e) {
            log.error("Failed to execute search", e);
            return Collections.emptyList();
        }
    }

    private List<ResearchAndDevelopmentEs> scrollByQuery(BoolQueryBuilder query) {
        List<ResearchAndDevelopmentEs> result = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.size(100);

        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest(this.indexName);
        searchRequest.scroll(scroll);
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();

            while (searchHits != null && searchHits.length > 0) {
                result.addAll(parseSearchResponse(searchResponse));
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = esClient.scroll(scrollRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
            }

            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            esClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);

            return result;
        } catch (IOException e) {
            log.error("Failed to execute scroll search", e);
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
