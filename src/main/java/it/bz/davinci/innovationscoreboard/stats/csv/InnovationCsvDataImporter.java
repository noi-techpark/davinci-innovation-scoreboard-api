package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.es.InnovationEsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.InnovationMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class InnovationCsvDataImporter implements StatsCsvDataImporter {

    private final StatsCsvParser<InnovationCsv> innovationCsvStatsCsvParser;
    private final InnovationEsDao innovationEsDao;

    public InnovationCsvDataImporter(InnovationEsDao innovationEsDao) {
        this.innovationEsDao = innovationEsDao;
        this.innovationCsvStatsCsvParser = new StatsCsvParser<>(InnovationCsv.class);
    }

    @Async
    @Override
    public void run(MultipartFile file) throws IOException {
        List<InnovationCsv> data = innovationCsvStatsCsvParser.parse(file);

        boolean indexCleaned = innovationEsDao.cleanIndex();

        if (indexCleaned) {
            data.forEach(record -> innovationEsDao.index(InnovationMapper.INSTANCE.toEs(record)));
        }

    }

}
