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

    public StatisticsResponseDto getDomesticResearchAndDevelopmentExpenditureInHouseDividedByTerritory() {
        StatisticsResponseDto result = new StatisticsResponseDto();

        final List<ResearchAndDevelopmentEs> data = researchAndDevelopmentEsDao.getDomesticResearchAndDevelopmentExpenditureInHouseDividedByTerritory();
        final Map<String, Collection<StatisticsResponsePerYearDto>> groupedStats = groupBySETTISTSEC2010(data);
        result.setStatistics(groupedStats);
        return result;
    }

    private Map<String, Collection<StatisticsResponsePerYearDto>> groupBySETTISTSEC2010(List<ResearchAndDevelopmentEs> data) {
        return data.stream()
                .collect(Collectors.groupingBy(
                        ResearchAndDevelopmentEs::getITTER107))
                .entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        row -> {
                            HashMap<String, StatisticsResponsePerYearDto> statisticsPerYear = new HashMap<>();
                            row.getValue().forEach(entry -> {

                                if (isNull(entry.getValue())) {
                                    return;
                                }

                                StatisticsResponsePerYearDto statisticsResponsePerYearDto;
                                if (statisticsPerYear.containsKey(entry.getTIME())) {
                                    statisticsResponsePerYearDto = statisticsPerYear.get(entry.getTIME());
                                } else {
                                    statisticsResponsePerYearDto = new StatisticsResponsePerYearDto();
                                    statisticsResponsePerYearDto.setTotal(BigDecimal.ZERO);
                                    statisticsPerYear.put(entry.getTIME(), statisticsResponsePerYearDto);
                                }

                                statisticsResponsePerYearDto.setYear(entry.getTIME());
                                statisticsResponsePerYearDto.setTotal(statisticsResponsePerYearDto.getTotal().add(entry.getValue()));

                                if (isNull(statisticsResponsePerYearDto.getGroups())) {
                                    statisticsResponsePerYearDto.setGroups(new ArrayList<>());
                                }

                                final Optional<StatisticsResponseGroupDto> first = statisticsResponsePerYearDto.getGroups().stream().filter(group -> "SETTISTSEC2010".equals(group.getId())).findFirst();

                                if (first.isPresent()) {
                                    first.get().getValues().put(entry.getSETTISTSEC2010(), entry.getValue());
                                } else {
                                    StatisticsResponseGroupDto groupDto = new StatisticsResponseGroupDto();
                                    groupDto.setId("SETTISTSEC2010");
                                    HashMap<String, BigDecimal> values = new HashMap<>();
                                    values.put(entry.getSETTISTSEC2010(), entry.getValue());
                                    groupDto.setValues(values);
                                    statisticsResponsePerYearDto.getGroups().add(groupDto);
                                }

                            });

                            return statisticsPerYear.values();
                        }
                ));
    }
}
