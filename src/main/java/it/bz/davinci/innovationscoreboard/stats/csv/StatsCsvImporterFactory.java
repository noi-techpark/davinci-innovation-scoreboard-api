// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StatsCsvImporterFactory {

    private final ResearchAndDevelopmentCsvImporter researchAndDevelopmentDataImporter;
    private final IctInCompaniesWithAtLeast10EmployeesCsvImporter ictInCompaniesWithAtLeast10EmployeesCsvImporter;
    private final InnovationInCompaniesWithAtLeast10EmployeesCsvImporter innovationInCompaniesWithAtLeast10EmployeesCsvImporter;

    public StatsCsvImporter getCsvDataImporter(@NotNull StatsType statsType) {
        switch (statsType) {
            case RESEARCH_AND_DEVELOPMENT:
                return researchAndDevelopmentDataImporter;
            case ICT_IN_COMPANIES_WITH_AT_LEAST_10_EMPLOYEES:
                return ictInCompaniesWithAtLeast10EmployeesCsvImporter;
            case INNOVATION_IN_COMPANIES_WITH_AT_LEAST_10_EMPLOYEES:
                return innovationInCompaniesWithAtLeast10EmployeesCsvImporter;
            default:
                throw new UnsupportedOperationException("No importer found for header information: " + statsType);
        }

    }

}
