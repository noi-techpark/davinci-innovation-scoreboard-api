// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.mapper;

import it.bz.davinci.innovationscoreboard.stats.csv.InnovationInCompaniesWithAtLeast10EmployeesCsv;
import it.bz.davinci.innovationscoreboard.stats.es.InnovationInCompaniesWithAtLeast10EmployeesEs;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmploymentDemographicMapper extends CsvMapper<InnovationInCompaniesWithAtLeast10EmployeesCsv, InnovationInCompaniesWithAtLeast10EmployeesEs> {

    EmploymentDemographicMapper INSTANCE = Mappers.getMapper(EmploymentDemographicMapper.class);

    @Mappings({})
    @Override
    InnovationInCompaniesWithAtLeast10EmployeesEs toEs(InnovationInCompaniesWithAtLeast10EmployeesCsv innovationInCompaniesWithAtLeast10EmployeesCsv);
}
