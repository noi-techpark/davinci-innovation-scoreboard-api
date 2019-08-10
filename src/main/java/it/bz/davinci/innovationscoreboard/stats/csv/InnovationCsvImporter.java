package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.es.InnovationEs;
import it.bz.davinci.innovationscoreboard.stats.mapper.InnovationMapper;
import org.springframework.stereotype.Service;

@Service
public class InnovationCsvImporter extends StatsCsvImporter<InnovationCsv, InnovationEs> {
    public InnovationCsvImporter(FileImportService fileImportService, EsDao<InnovationEs> esDao) {
        super(fileImportService, esDao, InnovationCsv.class, InnovationMapper.INSTANCE);
    }
}
