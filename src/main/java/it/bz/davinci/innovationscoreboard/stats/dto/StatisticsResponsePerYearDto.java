package it.bz.davinci.innovationscoreboard.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.stat.Statistics;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsResponsePerYearDto {
    private String year;
    private BigDecimal total;
    private List<StatisticsResponseGroupDto> groups;
}
