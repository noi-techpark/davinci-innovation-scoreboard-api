// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.davinci.innovationscoreboard.utils.es.DynamicTemplatesUtil;
import it.bz.davinci.innovationscoreboard.utils.es.EsIndexName;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
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
    public ResearchAndDevelopmentEsDao(RestHighLevelClient esClient, EsIndexName esIndexName, ObjectMapper objectMapper) {
        super(esIndexName.getPrefixedIndexName("research-and-development"), esClient, objectMapper);
    }

    public List<ResearchAndDevelopmentEs> getResearchAndDevelopmentPersonnelInHouseDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "RDPHIM"))
                .filter(QueryBuilders.termQuery("CORSO_LAUREA", "ALL"));

        return scrollByQuery(filter);
    }

    public List<ResearchAndDevelopmentEs> getDomesticResearchAndDevelopmentExpenditureInHouseDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "DRDEIM"))
                .filter(QueryBuilders.termQuery("ATTIVEC_CSC", "ALL"))
                .filter(QueryBuilders.termQuery("SETTISTSEC2010_B", "S1"))
                .filter(QueryBuilders.termQuery("TIPENTSPE", "99"))
                .filter(QueryBuilders.termQuery("SEXISTAT1", "9"))
                .filter(QueryBuilders.termQuery("TITOLO_STUDIO", "99"))
                .filter(QueryBuilders.termQuery("CORSO_LAUREA", "ALL"))
                .filter(QueryBuilders.termQuery("flagCodes", ""));

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

    @Override
    public boolean createIndex() {
        CreateIndexRequest request = new CreateIndexRequest(this.indexName);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 1)
        );

        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startArray("dynamic_templates");
                {
                    DynamicTemplatesUtil.setDynamicTemplateForStringsToKeyword(builder);
                }
                builder.endArray();
            }
            builder.endObject();
            request.mapping(builder);

            CreateIndexResponse createIndexResponse = esClient.indices()
                    .create(request, RequestOptions.DEFAULT);

            return createIndexResponse.isAcknowledged();

        } catch (IOException e) {
            log.error("Failed to define mapping for index: " + this.indexName, e);
            return false;
        }
    }


}
