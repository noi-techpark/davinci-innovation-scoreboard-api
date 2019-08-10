package it.bz.davinci.innovationscoreboard.stats.dto;

import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileImportDto {
    private String source;
    private LocalDateTime importDate;
    private FileImport.Status status;
}
