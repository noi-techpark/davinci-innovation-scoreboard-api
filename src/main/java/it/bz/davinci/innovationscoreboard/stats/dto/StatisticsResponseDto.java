package it.bz.davinci.innovationscoreboard.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponseDto {
    private Map<String, Collection<StatisticsResponsePerYearDto>> statistics;
}
