package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEs;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.CsvMapper;
import it.bz.davinci.innovationscoreboard.stats.mapper.ResearchAndDevelopmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ResearchAndDevelopmentCsvImporter extends StatsCsvImporter<ResearchAndDevelopmentCsv, ResearchAndDevelopmentEs>{
    public ResearchAndDevelopmentCsvImporter(FileImportService fileImportService, EsDao<ResearchAndDevelopmentEs> esDao) {
        super(fileImportService, esDao, ResearchAndDevelopmentCsv.class, ResearchAndDevelopmentMapper.INSTANCE);
    }
}
