package it.bz.davinci.innovationscoreboard.stats.csv;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
@AllArgsConstructor
public class StatsCsvImporterFactory {

    private final ResearchAndDevelopmentCsvImporter researchAndDevelopmentDataImporter;
    private final InnovationCsvImporter innovationCsvDataImporter;
    private final EmploymentDemographicCsvImporter employmentDemographicCsvDataImporter;

    public StatsCsvImporter getCsvDataImporter(@NotNull String header) {

        if (Objects.isNull(header)) {
            throw new IllegalArgumentException("Header cannot be null");
        }

        String csvType = header.replaceAll("[\uFEFF-\uFFFF]", "");
        switch (csvType.trim()) {
            case ResearchAndDevelopmentCsv.SUPPORTED_HEADER:
                return researchAndDevelopmentDataImporter;
            case InnovationCsv.SUPPORTED_HEADER:
                return innovationCsvDataImporter;
            case EmploymentDemographicCsv.SUPPORTED_HEADER:
                return employmentDemographicCsvDataImporter;
            default:
                throw new UnsupportedOperationException("No importer found for header information: " + csvType);
        }

    }

}
