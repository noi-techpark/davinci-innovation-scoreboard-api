package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseDto;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseGroupDto;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponsePerYearDto;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEs;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEsDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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
        final List<ResearchAndDevelopmentEs> data = researchAndDevelopmentEsDao.getResearchAndDevelopmentPersonnelInHouseDividedByTerritory();
        final Map<String, Collection<StatisticsResponsePerYearDto>> groupedStats = data.stream()
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

                                if ("S1".equals(entry.getSETTISTSEC2010()) && "total".equals(entry.getProfessionalStatus()) && "total".equals(entry.getGender()) && "total".equals(entry.getQualificationISCED())) {
                                    statisticsResponsePerYearDto.setTotal(entry.getValue());
                                } else {

                                    if (isNull(statisticsResponsePerYearDto.getGroups())) {
                                        statisticsResponsePerYearDto.setGroups(new ArrayList<>());
                                    }

                                    if ("total".equals(entry.getProfessionalStatus()) && "total".equals(entry.getGender()) && "total".equals(entry.getQualificationISCED())) {
                                        createOrUpdateGroup(statisticsResponsePerYearDto.getGroups(), "SETTISTSEC2010", entry.getSETTISTSEC2010(), entry.getValue());
                                    } else if ("S1".equals(entry.getSETTISTSEC2010()) && "total".equals(entry.getGender()) && "total".equals(entry.getQualificationISCED())) {
                                        createOrUpdateGroup(statisticsResponsePerYearDto.getGroups(), "PROFILO_PROF", entry.getPROFILO_PROF(), entry.getValue());
                                        createOrUpdateGroup(statisticsResponsePerYearDto.getGroups(), "PROFILO_PROF", "others", statisticsResponsePerYearDto.getTotal().subtract(entry.getValue()));
                                    } else if ("S1".equals(entry.getSETTISTSEC2010()) && "total".equals(entry.getProfessionalStatus()) && "total".equals(entry.getQualificationISCED())) {
                                        createOrUpdateGroup(statisticsResponsePerYearDto.getGroups(), "SEXISTAT1", entry.getSEXISTAT1(), entry.getValue());
                                    }
                                }
                            });

                            return statisticsPerYear.values();
                        }
                ));

        result.setStatistics(groupedStats);
        return result;
    }

    private void createOrUpdateGroup(@NotNull List<StatisticsResponseGroupDto> groups, @NotNull String id, String groupIdentifier, BigDecimal groupValue) {
        final Optional<StatisticsResponseGroupDto> groupDtoOptional = groups.stream().filter(group -> id.equals(group.getId())).findFirst();

        if (groupDtoOptional.isPresent()) {
            groupDtoOptional.get().getValues().put(groupIdentifier, groupValue);
        } else {
            StatisticsResponseGroupDto groupDto = new StatisticsResponseGroupDto();
            groupDto.setId(id);
            HashMap<String, BigDecimal> values = new HashMap<>();
            values.put(groupIdentifier, groupValue);
            groupDto.setValues(values);
            groups.add(groupDto);
        }
    }

    public StatisticsResponseDto getDomesticResearchAndDevelopmentExpenditureInHouseDividedByTerritory() {
        StatisticsResponseDto result = new StatisticsResponseDto();

        final List<ResearchAndDevelopmentEs> data = researchAndDevelopmentEsDao.getDomesticResearchAndDevelopmentExpenditureInHouseDividedByTerritory();
        final Map<String, Collection<StatisticsResponsePerYearDto>> groupedStats = groupBySETTISTSEC2010(data, BigDecimal.valueOf(1000));
        result.setStatistics(groupedStats);
        return result;
    }

    private Map<String, Collection<StatisticsResponsePerYearDto>> groupBySETTISTSEC2010(List<ResearchAndDevelopmentEs> data, BigDecimal multiplier) {
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
                                String groupIdentifier = entry.getSETTISTSEC2010();
                                BigDecimal groupValue = entry.getValue().multiply(multiplier);

                                if ("S1".equals(groupIdentifier)) {
                                    statisticsResponsePerYearDto.setTotal(groupValue);
                                } else {

                                    if (isNull(statisticsResponsePerYearDto.getGroups())) {
                                        statisticsResponsePerYearDto.setGroups(new ArrayList<>());
                                    }

                                    final Optional<StatisticsResponseGroupDto> first = statisticsResponsePerYearDto.getGroups().stream().filter(group -> "SETTISTSEC2010".equals(group.getId())).findFirst();


                                    if (first.isPresent()) {
                                        first.get().getValues().put(groupIdentifier, groupValue);
                                    } else {
                                        StatisticsResponseGroupDto groupDto = new StatisticsResponseGroupDto();
                                        groupDto.setId("SETTISTSEC2010");
                                        HashMap<String, BigDecimal> values = new HashMap<>();
                                        values.put(groupIdentifier, groupValue);
                                        groupDto.setValues(values);
                                        statisticsResponsePerYearDto.getGroups().add(groupDto);
                                    }
                                }
                            });

                            return statisticsPerYear.values();
                        }
                ));
    }
}
