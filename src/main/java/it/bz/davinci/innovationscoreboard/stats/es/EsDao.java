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

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Slf4j
public abstract class EsDao<T> {

    protected final String indexName;
    protected final RestHighLevelClient esClient;
    protected final ObjectMapper objectMapper;

    public void index(T document) {
        IndexRequest indexRequest = new IndexRequest(indexName)
                .source(objectMapper.convertValue(document, Map.class), XContentType.JSON);

        try {
            esClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("indexing document");
        } catch (IOException e) {
            log.error("Failed to index document", e);
        }
    }

    public abstract boolean createIndex();

    public boolean deleteIndex() {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        AcknowledgedResponse deleteIndexResponse;

        try {
            deleteIndexResponse = esClient.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Failed to delete index document", e);
            return false;
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                return true;
            }
            log.error("Failed to delete index document", e);
            return false;
        }

        return deleteIndexResponse.isAcknowledged();
    }

    public boolean cleanIndex() {
        final boolean indexDeleted = deleteIndex();
        final boolean indexCreated = createIndex();
        return indexDeleted && indexCreated;
    }

}
