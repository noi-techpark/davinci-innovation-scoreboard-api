package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.davinci.innovationscoreboard.utils.es.DynamicTemplatesUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class InnovationEsDao extends EsDao<InnovationEs> {

    public InnovationEsDao(RestHighLevelClient esClient, ObjectMapper objectMapper) {
        super("innovation", esClient, objectMapper);
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
