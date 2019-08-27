package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseDto;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseGroupDto;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponsePerYearDto;
import it.bz.davinci.innovationscoreboard.stats.es.EmploymentDemographicEs;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEs;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEsDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class ResearchAndDevelopmentAggregator {

    private final ResearchAndDevelopmentEsDao researchAndDevelopmentEsDao;

    public StatisticsResponseDto getResearchAndDevelopmentPersonnelInHouseDividedByTerritory() {
        StatisticsResponseDto result = new StatisticsResponseDto();

        //TODO
        final List<ResearchAndDevelopmentEs> researchAndDevelopmentPersonnel = researchAndDevelopmentEsDao.getResearchAndDevelopmentPersonnelInHouseDividedByTerritory();

        return result;
    }

}
