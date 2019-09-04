package it.bz.davinci.innovationscoreboard.utils.es;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class DynamicTemplatesUtil {

    public static void setDynamicTemplateForStringsToKeyword(XContentBuilder builder) throws IOException {
        builder.startObject();
        {
            builder.startObject("keyword_fields");
            {
                builder.field("match", "*");
                builder.field("match_mapping_type", "string");
                builder.startObject("mapping");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
    }
}
