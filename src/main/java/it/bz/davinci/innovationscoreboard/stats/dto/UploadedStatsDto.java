package it.bz.davinci.innovationscoreboard.stats.dto;

import it.bz.davinci.innovationscoreboard.stats.domain.FileImport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadedStatsDto {
    private String source;
    private LocalDateTime date;
    private FileImport.Status status;
}
