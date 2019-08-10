package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.es.EmploymentDemographicEs;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.EmploymentDemographicMapper;
import org.springframework.stereotype.Service;

@Service
public class EmploymentDemographicCsvImporter extends StatsCsvImporter<EmploymentDemographicCsv, EmploymentDemographicEs> {

    public EmploymentDemographicCsvImporter(FileImportService fileImportService, EsDao<EmploymentDemographicEs> esDao) {
        super(fileImportService, esDao, EmploymentDemographicCsv.class, EmploymentDemographicMapper.INSTANCE);
    }
}
