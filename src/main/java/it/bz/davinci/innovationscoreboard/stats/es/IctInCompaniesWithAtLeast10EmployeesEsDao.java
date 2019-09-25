package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.davinci.innovationscoreboard.utils.es.DynamicTemplatesUtil;
import it.bz.davinci.innovationscoreboard.utils.es.EsIndexName;
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
public class IctInCompaniesWithAtLeast10EmployeesEsDao extends EsDao<IctInCompaniesWithAtLeast10EmployeesEs> {

    public IctInCompaniesWithAtLeast10EmployeesEsDao(RestHighLevelClient esClient, EsIndexName esIndexName, ObjectMapper objectMapper) {
        super(esIndexName.getPrefixedIndexName("ict-in-companies-with-at-least-10-employees"), esClient, objectMapper);
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
