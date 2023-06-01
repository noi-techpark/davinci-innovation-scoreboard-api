// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.mapper;

import it.bz.davinci.innovationscoreboard.stats.csv.IctInCompaniesWithAtLeast10EmployeesCsv;
import it.bz.davinci.innovationscoreboard.stats.es.IctInCompaniesWithAtLeast10EmployeesEs;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface IctInCompaniesWithAtLeast10EmployeesMapper extends CsvMapper<IctInCompaniesWithAtLeast10EmployeesCsv, IctInCompaniesWithAtLeast10EmployeesEs> {

    IctInCompaniesWithAtLeast10EmployeesMapper INSTANCE = Mappers.getMapper(IctInCompaniesWithAtLeast10EmployeesMapper.class);

    @Mappings({})
    @Override
    IctInCompaniesWithAtLeast10EmployeesEs toEs(IctInCompaniesWithAtLeast10EmployeesCsv ictInCompaniesWithAtLeast10EmployeesCsv);
}
