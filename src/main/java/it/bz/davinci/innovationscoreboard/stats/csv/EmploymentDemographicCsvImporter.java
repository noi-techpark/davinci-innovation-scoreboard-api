package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportLogService;
import it.bz.davinci.innovationscoreboard.stats.es.EmploymentDemographicEs;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.EmploymentDemographicMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EmploymentDemographicCsvImporter extends StatsCsvImporter<EmploymentDemographicCsv, EmploymentDemographicEs> {

    public EmploymentDemographicCsvImporter(FileImportLogService fileImportLogService, EsDao<EmploymentDemographicEs> esDao, ApplicationEventPublisher publisher) {
        super(fileImportLogService, esDao, EmploymentDemographicCsv.class, EmploymentDemographicMapper.INSTANCE, publisher);
    }
}
