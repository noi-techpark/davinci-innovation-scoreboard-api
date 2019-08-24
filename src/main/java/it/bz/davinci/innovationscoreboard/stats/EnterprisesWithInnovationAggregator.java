package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseDto;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseGroupDto;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponsePerYearDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class EnterprisesWithInnovationAggregator {

    public StatisticsResponseDto getEnterprisesWithInnovationActivitiesDividedByTerritory() {

        StatisticsResponseDto result = new StatisticsResponseDto();

        HashMap<String, List<StatisticsResponsePerYearDto>> perTerritoryStatistics = new HashMap<>();
        perTerritoryStatistics.put("IT", getStatisticsForIT());
        perTerritoryStatistics.put("ITD1", getStatisticsForSouthTyrol());
        perTerritoryStatistics.put("ITD2", getStatisticsForTrento());
        perTerritoryStatistics.put("ITC4", getStatisticsForLombardia());

        result.setStatistics(perTerritoryStatistics);

        return result;
    }

    private HashMap<String, BigDecimal> getValues(BigDecimal ptcon, BigDecimal omkon, BigDecimal ptcomk) {
        HashMap<String, BigDecimal> result = new HashMap<>();
        result.put("PTCON", ptcon);
        result.put("OMKON", omkon);
        result.put("PTCOMK", ptcomk);

        return result;
    }

    private List<StatisticsResponsePerYearDto> getStatisticsForIT() {
        return getStatisticsForTerritory(BigDecimal.valueOf(84701), BigDecimal.valueOf(14874), BigDecimal.valueOf(26679), BigDecimal.valueOf(43147),
                BigDecimal.valueOf(68204), BigDecimal.valueOf(17674), BigDecimal.valueOf(19416), BigDecimal.valueOf(31113),
                BigDecimal.valueOf(76895), BigDecimal.valueOf(19268), BigDecimal.valueOf(16757), BigDecimal.valueOf(40870));
    }

    private List<StatisticsResponsePerYearDto> getStatisticsForSouthTyrol() {
        return getStatisticsForTerritory(BigDecimal.valueOf(968), BigDecimal.valueOf(908), BigDecimal.valueOf(931), BigDecimal.valueOf(135), BigDecimal.valueOf(235), BigDecimal.valueOf(195), BigDecimal.valueOf(347), BigDecimal.valueOf(278), BigDecimal.valueOf(217), BigDecimal.valueOf(486), BigDecimal.valueOf(394), BigDecimal.valueOf(518));
    }

    private List<StatisticsResponsePerYearDto> getStatisticsForTrento() {
        return getStatisticsForTerritory(BigDecimal.valueOf(897), BigDecimal.valueOf(885), BigDecimal.valueOf(759), BigDecimal.valueOf(158), BigDecimal.valueOf(193), BigDecimal.valueOf(172), BigDecimal.valueOf(297), BigDecimal.valueOf(291), BigDecimal.valueOf(190), BigDecimal.valueOf(442), BigDecimal.valueOf(402), BigDecimal.valueOf(396));
    }

    private List<StatisticsResponsePerYearDto> getStatisticsForLombardia() {
        return getStatisticsForTerritory(BigDecimal.valueOf(20978), BigDecimal.valueOf(17323), BigDecimal.valueOf(20535), BigDecimal.valueOf(3922), BigDecimal.valueOf(4454), BigDecimal.valueOf(5095), BigDecimal.valueOf(5833), BigDecimal.valueOf(4297), BigDecimal.valueOf(4524), BigDecimal.valueOf(11223), BigDecimal.valueOf(8572), BigDecimal.valueOf(10916));
    }

    private List<StatisticsResponsePerYearDto> getStatisticsForTerritory(BigDecimal year2012All, BigDecimal year2012Ptcon, BigDecimal year2012omkon, BigDecimal year2012ptcomk,
                                                                         BigDecimal year2014All, BigDecimal year2014Ptcon, BigDecimal year2014omkon, BigDecimal year2014ptcomk,
                                                                         BigDecimal year2016All, BigDecimal year2016Ptcon, BigDecimal year2016omkon, BigDecimal year2016ptcomk) {
        List<StatisticsResponsePerYearDto> result = new ArrayList<>();
        StatisticsResponsePerYearDto year1 = new StatisticsResponsePerYearDto();
        year1.setYear("2012");
        year1.setTotal(year2012All);
        year1.setGroups(Collections.singletonList(StatisticsResponseGroupDto.builder()
                .id("FORMA_INNOVAZ")
                .values(getValues(year2012Ptcon, year2012omkon, year2012ptcomk))
                .build()));

        StatisticsResponsePerYearDto year2 = new StatisticsResponsePerYearDto();
        year2.setYear("2014");
        year2.setTotal(year2014All);
        year2.setGroups(Collections.singletonList(StatisticsResponseGroupDto.builder()
                .id("FORMA_INNOVAZ")
                .values(getValues(year2014Ptcon, year2014omkon, year2014ptcomk))
                .build()));

        StatisticsResponsePerYearDto year3 = new StatisticsResponsePerYearDto();
        year3.setYear("2016");
        year3.setTotal(year2016All);
        year3.setGroups(Collections.singletonList(StatisticsResponseGroupDto.builder()
                .id("FORMA_INNOVAZ")
                .values(getValues(year2016Ptcon, year2016omkon, year2016ptcomk))
                .build()));

        result.add(year1);
        result.add(year2);
        result.add(year3);

        return result;
    }

}
