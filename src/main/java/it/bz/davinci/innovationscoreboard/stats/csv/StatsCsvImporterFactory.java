package it.bz.davinci.innovationscoreboard.stats.csv;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class StatsCsvImporterFactory {

    private final ResearchAndDevelopmentCsvDataImporter researchAndDevelopmentDataImporter;
    private final InnovationCsvDataImporter innovationCsvDataImporter;

    public StatsCsvDataImporter getCsvDataImporter(String header) {

        if (Objects.isNull(header)) {
            throw new IllegalArgumentException("Header cannot be null");
        }

        switch (header) {
            case ResearchAndDevelopmentCsv.SUPPORTED_HEADER:
                return researchAndDevelopmentDataImporter;
            case InnovationCsv.SUPPORTED_HEADER:
                return innovationCsvDataImporter;
            default:
                throw new UnsupportedOperationException("No importer found for header information: " + header);
        }

    }

}
