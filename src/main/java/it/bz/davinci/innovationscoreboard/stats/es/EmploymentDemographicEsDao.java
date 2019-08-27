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
public class EmploymentDemographicEsDao extends EsDao<EmploymentDemographicEs> {

    public EmploymentDemographicEsDao(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        super("employment-demographic", esClient, objectMapper);
    }

    public List<EmploymentDemographicEs> getEnterprisesWithInnovationActivitiesDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "POPI"))
                .filter(QueryBuilders.termQuery("ATECO_2007.keyword", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT.keyword", "W_GE10"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getEnterprisesWithInnovationActivitiesInItalyDividedByNACE() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "POPI"))
                .filter(QueryBuilders.termQuery("ITTER107.keyword", "IT"))
                .filter(QueryBuilders.termQuery("CLLVT.keyword", "W_GE10"))
                .filter(QueryBuilders.termQuery("FORMA_INNOVAZ.keyword", "ALL"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getEnterprisesThatHaveIntroducedProductOrProcessInnovationsDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "PTCS"))
                .filter(QueryBuilders.termQuery("ATECO_2007.keyword", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT.keyword", "W_GE10"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getEnterprisesThatHaveIntroducedProductOrProcessInnovationsInItalyDividedByNace() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "PTCS"))
                .filter(QueryBuilders.termQuery("ITTER107.keyword", "IT"))
                .filter(QueryBuilders.termQuery("CLLVT.keyword", "W_GE10"))
                .filter(QueryBuilders.termQuery("FORMA_INNOVAZ.keyword", "ALL"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getInnovationExpenditureDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "RALLX"))
                .filter(QueryBuilders.termQuery("ATECO_2007.keyword", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT.keyword", "W_GE10"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getInnovationExpenditureInItalyDividedByNace() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "RALLX"))
                .filter(QueryBuilders.termQuery("ITTER107.keyword", "IT"))
                .filter(QueryBuilders.termQuery("CLLVT.keyword", "W_GE10"))
                .filter(QueryBuilders.termQuery("FORMA_INNOVAZ.keyword", "ALL"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getInnovationExpenditurePerNumberOfPersonsEmployedDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS.keyword", "RXEPPI"))
                .filter(QueryBuilders.termQuery("ATECO_2007.keyword", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT.keyword", "W_GE10"));

        return searchByQuery(filter);
    }

    private List<EmploymentDemographicEs> searchByQuery(BoolQueryBuilder query) {
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
