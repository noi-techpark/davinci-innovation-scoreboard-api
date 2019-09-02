package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEs;
import it.bz.davinci.innovationscoreboard.stats.mapper.ResearchAndDevelopmentMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ResearchAndDevelopmentCsvImporter extends StatsCsvImporter<ResearchAndDevelopmentCsv, ResearchAndDevelopmentEs> {
    public ResearchAndDevelopmentCsvImporter(FileImportService fileImportService, EsDao<ResearchAndDevelopmentEs> esDao, ApplicationEventPublisher publisher) {
        super(fileImportService, esDao, ResearchAndDevelopmentCsv.class, ResearchAndDevelopmentMapper.INSTANCE, publisher);
    }
}
