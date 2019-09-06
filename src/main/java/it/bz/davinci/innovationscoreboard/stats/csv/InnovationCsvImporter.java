package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportLogService;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.es.InnovationEs;
import it.bz.davinci.innovationscoreboard.stats.mapper.InnovationMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class InnovationCsvImporter extends StatsCsvImporter<InnovationCsv, InnovationEs> {
    public InnovationCsvImporter(FileImportLogService fileImportLogService, EsDao<InnovationEs> esDao, ApplicationEventPublisher publisher) {
        super(fileImportLogService, esDao, InnovationCsv.class, InnovationMapper.INSTANCE, publisher);
    }
}
