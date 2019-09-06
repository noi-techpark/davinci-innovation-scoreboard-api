package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportLogService;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.es.IctInCompaniesWithAtLeast10EmployeesEs;
import it.bz.davinci.innovationscoreboard.stats.mapper.IctInCompaniesWithAtLeast10EmployeesMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class IctInCompaniesWithAtLeast10EmployeesCsvImporter extends StatsCsvImporter<IctInCompaniesWithAtLeast10EmployeesCsv, IctInCompaniesWithAtLeast10EmployeesEs> {
    public IctInCompaniesWithAtLeast10EmployeesCsvImporter(FileImportLogService fileImportLogService, EsDao<IctInCompaniesWithAtLeast10EmployeesEs> esDao, ApplicationEventPublisher publisher) {
        super(fileImportLogService, esDao, IctInCompaniesWithAtLeast10EmployeesCsv.class, IctInCompaniesWithAtLeast10EmployeesMapper.INSTANCE, publisher);
    }
}
