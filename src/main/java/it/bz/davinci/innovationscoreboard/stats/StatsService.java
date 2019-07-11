package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.es.EsStats;
import it.bz.davinci.innovationscoreboard.stats.es.StatsEsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StatsService {

    private final StatsCsvParser statsCsvParser;
    private final StatsEsDao statsEsDao;

    @Autowired
    public StatsService(StatsCsvParser statsCsvParser, StatsEsDao statsEsDao) {
        this.statsCsvParser = statsCsvParser;
        this.statsEsDao = statsEsDao;
    }

    public void uploadData(MultipartFile file) throws IOException {

        List<Stats> stats = statsCsvParser.parse(file);

        stats.stream().map(entry ->
                EsStats.builder()
                        .firstName(entry.getFirstName())
                        .lastName(entry.getLastName())
                        .build()
        ).forEach(statsEsDao::index);

    }


}
