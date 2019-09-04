package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.davinci.innovationscoreboard.utils.es.DynamicTemplatesUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "POPI"))
                .filter(QueryBuilders.termQuery("ATECO_2007", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT", "W_GE10"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getEnterprisesWithInnovationActivitiesInItalyDividedByNACE() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "POPI"))
                .filter(QueryBuilders.termQuery("ITTER107", "IT"))
                .filter(QueryBuilders.termQuery("CLLVT", "W_GE10"))
                .filter(QueryBuilders.termQuery("FORMA_INNOVAZ", "ALL"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getEnterprisesThatHaveIntroducedProductOrProcessInnovationsDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "PTCS"))
                .filter(QueryBuilders.termQuery("ATECO_2007", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT", "W_GE10"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getEnterprisesThatHaveIntroducedProductOrProcessInnovationsInItalyDividedByNace() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "PTCS"))
                .filter(QueryBuilders.termQuery("ITTER107", "IT"))
                .filter(QueryBuilders.termQuery("CLLVT", "W_GE10"))
                .filter(QueryBuilders.termQuery("FORMA_INNOVAZ", "ALL"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getInnovationExpenditureDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "RALLX"))
                .filter(QueryBuilders.termQuery("ATECO_2007", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT", "W_GE10"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getInnovationExpenditureInItalyDividedByNace() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "RALLX"))
                .filter(QueryBuilders.termQuery("ITTER107", "IT"))
                .filter(QueryBuilders.termQuery("CLLVT", "W_GE10"))
                .filter(QueryBuilders.termQuery("FORMA_INNOVAZ", "ALL"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getInnovationExpenditurePerNumberOfPersonsEmployedDividedByTerritory() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "RXEPPI"))
                .filter(QueryBuilders.termQuery("ATECO_2007", "00100"))
                .filter(QueryBuilders.termQuery("CLLVT", "W_GE10"));

        return searchByQuery(filter);
    }

    public List<EmploymentDemographicEs> getInnovationExpenditurePerNumberOfPersonsEmployedInItalyDividedByNace() {
        final BoolQueryBuilder filter = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("TIPO_DATO_CIS", "RXEPPI"))
                .filter(QueryBuilders.termQuery("ITTER107", "IT"))
                .filter(QueryBuilders.termQuery("CLLVT", "W_GE10"))
                .filter(QueryBuilders.termQuery("FORMA_INNOVAZ", "ALL"));

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
