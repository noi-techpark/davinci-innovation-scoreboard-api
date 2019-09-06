package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StatsCsvImporterFactory {

    private final ResearchAndDevelopmentCsvImporter researchAndDevelopmentDataImporter;
    private final IctInCompaniesWithAtLeast10EmployeesCsvImporter innovationCsvDataImporter;
    private final EmploymentDemographicCsvImporter employmentDemographicCsvDataImporter;

    public StatsCsvImporter getCsvDataImporter(@NotNull StatsType statsType) {
        switch (statsType) {
            case RESEARCH_AND_DEVELOPMENT:
                return researchAndDevelopmentDataImporter;
            case INNOVATION_IN_COMPANIES_WITH_AT_LEAST_10_EMPLOYEES:
                return innovationCsvDataImporter;
            case ICT_IN_COMPANIES_WITH_AT_LEAST_10_EMPLOYEES:
                return employmentDemographicCsvDataImporter;
            default:
                throw new UnsupportedOperationException("No importer found for header information: " + statsType);
        }

    }

}
