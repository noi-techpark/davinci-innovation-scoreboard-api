package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ResearchAndDevelopmentEsDao {

    private final String INDEX_NAME = "research-and-development";
    private final RestHighLevelClient esClient;
    private final ObjectMapper objectMapper;


    public void index(ResearchAndDevelopmentEs document) {
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME)
                .source(objectMapper.convertValue(document, Map.class), XContentType.JSON);

        try {
            esClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Failed to index document", e);
        }
    }

    public boolean cleanIndex() {

        DeleteIndexRequest request = new DeleteIndexRequest("research-and-development");
        AcknowledgedResponse deleteIndexResponse;

        try {
            deleteIndexResponse = esClient.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Failed to delete index document", e);
            return false;
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {
                return true;
            }
            log.error("Failed to delete index document", exception);
            return false;
        }

        return deleteIndexResponse.isAcknowledged();
    }

}
