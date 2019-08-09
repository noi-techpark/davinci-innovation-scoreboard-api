package it.bz.davinci.innovationscoreboard.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UploadHistoryResponseDto {
    private List<UploadedStatsDto> uploadedStats;
}
