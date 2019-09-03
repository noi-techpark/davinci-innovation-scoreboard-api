package it.bz.davinci.innovationscoreboard.stats.dto;

import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileImportDto {
    private Integer id;
    private String source;
    private LocalDateTime importDate;
    private FileImport.Status status;
    private String logs;
    private StatsType type;
}
