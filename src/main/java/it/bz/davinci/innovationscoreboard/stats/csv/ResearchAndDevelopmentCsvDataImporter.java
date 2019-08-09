package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.StatsCsvParser;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.ResearchAndDevelopmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ResearchAndDevelopmentCsvDataImporter implements StatsCsvDataImporter {

    private final static StatsCsvParser<ResearchAndDevelopmentCsv> researchAndDevelopmentCsvStatsCsvParser = new StatsCsvParser<>(ResearchAndDevelopmentCsv.class);

    private final ResearchAndDevelopmentEsDao researchAndDevelopmentEsDao;

    @Async
    @Override
    public void run(MultipartFile file) throws IOException {
        List<ResearchAndDevelopmentCsv> data = researchAndDevelopmentCsvStatsCsvParser.parse(file);

        boolean indexCleaned = researchAndDevelopmentEsDao.cleanIndex();

        if (indexCleaned) {
            data.forEach(record -> researchAndDevelopmentEsDao.index(ResearchAndDevelopmentMapper.INSTANCE.toEs(record)));
        }

    }

}
