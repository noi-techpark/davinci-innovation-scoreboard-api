package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporter;
import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporterFactory;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@AllArgsConstructor
public class StatsImporter {

    private final StatsCsvImporterFactory statsCsvImporterFactory;
    private final FileImportService fileImportService;

    public FileImportDto importFile(MultipartFile file) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), UTF_8))) {
            String csvHeader = bufferedReader.readLine();
            StatsCsvImporter csvDataImporter = statsCsvImporterFactory.getCsvDataImporter(csvHeader);
            final FileImportDto uploadedFile = fileImportService.save(FileImportDto.builder()
                    .importDate(LocalDateTime.now())
                    .source(file.getOriginalFilename())
                    .status(FileImport.Status.UPLOADED)
                    .build());
            csvDataImporter.importFile(file, uploadedFile.getId());
            return uploadedFile;
        }

    }


}
