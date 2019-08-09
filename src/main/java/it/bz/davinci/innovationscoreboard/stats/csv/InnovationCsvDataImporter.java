package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.StatsCsvParser;
import it.bz.davinci.innovationscoreboard.stats.es.InnovationEsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.InnovationMapper;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class InnovationCsvDataImporter implements StatsCsvDataImporter {

    private final static StatsCsvParser<InnovationCsv> innovationCsvStatsCsvParser = new StatsCsvParser<>(InnovationCsv.class);

    private final InnovationEsDao innovationEsDao;

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
