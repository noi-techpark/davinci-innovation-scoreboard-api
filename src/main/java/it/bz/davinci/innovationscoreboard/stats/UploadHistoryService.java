package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.domain.StatsStatus;
import it.bz.davinci.innovationscoreboard.stats.dto.UploadHistoryResponseDto;
import it.bz.davinci.innovationscoreboard.stats.dto.UploadedStatsDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadHistoryService {

    public UploadHistoryResponseDto getHistory() {

        UploadedStatsDto stats1 = new UploadedStatsDto();
        stats1.setSource("stats1.csv");
        stats1.setStatus(StatsStatus.UPLOADED);
        stats1.setDate(LocalDateTime.now().minusMinutes(10));

        UploadedStatsDto stats2 = new UploadedStatsDto();
        stats2.setSource("stats2.csv");
        stats2.setStatus(StatsStatus.PROCESSING);
        stats2.setDate(LocalDateTime.now().minusMinutes(20));

        UploadedStatsDto stats3 = new UploadedStatsDto();
        stats3.setSource("stats3.csv");
        stats3.setStatus(StatsStatus.SUCCESS);
        stats3.setDate(LocalDateTime.now().minusDays(2));

        UploadedStatsDto stats4 = new UploadedStatsDto();
        stats4.setSource("stats4.csv");
        stats4.setStatus(StatsStatus.ERROR);
        stats4.setDate(LocalDateTime.now().minusDays(3));

        List<UploadedStatsDto> result = new ArrayList<>();
        result.add(stats1);
        result.add(stats2);
        result.add(stats3);
        result.add(stats4);

        return new UploadHistoryResponseDto(result);

    }
}
