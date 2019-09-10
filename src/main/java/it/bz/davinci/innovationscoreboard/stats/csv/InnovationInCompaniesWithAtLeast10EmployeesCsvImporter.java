package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportLogService;
import it.bz.davinci.innovationscoreboard.stats.es.InnovationInCompaniesWithAtLeast10EmployeesEs;
import it.bz.davinci.innovationscoreboard.stats.es.InnovationInCompaniesWithAtLeast10EmployeesEsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.EmploymentDemographicMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class InnovationInCompaniesWithAtLeast10EmployeesCsvImporter extends StatsCsvImporter<InnovationInCompaniesWithAtLeast10EmployeesCsv, InnovationInCompaniesWithAtLeast10EmployeesEs> {

    public InnovationInCompaniesWithAtLeast10EmployeesCsvImporter(FileImportLogService fileImportLogService, InnovationInCompaniesWithAtLeast10EmployeesEsDao esDao, ApplicationEventPublisher publisher) {
        super(fileImportLogService, esDao, InnovationInCompaniesWithAtLeast10EmployeesCsv.class, EmploymentDemographicMapper.INSTANCE, publisher);
    }
}
