package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.es.EmploymentDemographicEsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.EmploymentDemographicMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EmploymentDemographicCsvDataImporter implements StatsCsvDataImporter {

    private final StatsCsvParser<EmploymentDemographicCsv> employmentDemographicCsvDataParser;
    private final EmploymentDemographicEsDao employmentDemographicEsDao;

    public EmploymentDemographicCsvDataImporter(EmploymentDemographicEsDao employmentDemographicEsDao) {
        this.employmentDemographicEsDao = employmentDemographicEsDao;
        this.employmentDemographicCsvDataParser = new StatsCsvParser<>(EmploymentDemographicCsv.class);
    }

    @Async
    @Override
    public void run(MultipartFile file) throws IOException {
        List<EmploymentDemographicCsv> data = employmentDemographicCsvDataParser.parse(file);

        boolean indexCleaned = employmentDemographicEsDao.cleanIndex();

        if (indexCleaned) {
            data.forEach(record -> employmentDemographicEsDao.index(EmploymentDemographicMapper.INSTANCE.toEs(record)));
        }

    }

}
