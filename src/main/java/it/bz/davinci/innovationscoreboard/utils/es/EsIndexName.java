package it.bz.davinci.innovationscoreboard.utils.es;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EsIndexName {

    private final String prefix;

    public EsIndexName(@Value("${elasticsearch.namespace.prefix}") String prefix) {
        this.prefix = prefix;
    }

    @NotNull
    public String getPrefixedIndexName(@NotNull String indexName) {
        if (prefix.isEmpty()) {
            return indexName;
        }

        return prefix.endsWith("-") ? prefix + indexName : prefix + "-" + indexName;
    }
}
