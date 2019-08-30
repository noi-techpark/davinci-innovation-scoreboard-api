package it.bz.davinci.innovationscoreboard.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsResponseGroupDto {
    private String id;
    private Map<String, BigDecimal> values;
}
