package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static it.bz.davinci.innovationscoreboard.stats.model.StatsType.RESEARCH_AND_DEVELOPMENT;

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

        StatsType csvType = StatsType.findByCsvHeader(header.replaceAll("[\uFEFF-\uFFFF]", "").trim())
                .orElseThrow(() -> new UnsupportedOperationException("No importer found for header information: " + header));

        switch (csvType) {
            case RESEARCH_AND_DEVELOPMENT:
                return researchAndDevelopmentDataImporter;
            case INNOVATION_IN_COMPANIES_WITH_AT_LEAST_10_EMPLOYEES:
                return innovationCsvDataImporter;
            case ICT_IN_COMPANIES_WITH_AT_LEAST_10_EMPLOYEES:
                return employmentDemographicCsvDataImporter;
            default:
                throw new UnsupportedOperationException("No importer found for header information: " + csvType);
        }

    }

}
