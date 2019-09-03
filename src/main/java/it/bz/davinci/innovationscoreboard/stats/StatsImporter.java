package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporter;
import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporterFactory;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import it.bz.davinci.innovationscoreboard.utils.TempFileUtil;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@AllArgsConstructor
public class StatsImporter {

    private final StatsCsvImporterFactory statsCsvImporterFactory;
    private final FileImportService fileImportService;

    public FileImportDto importFile(MultipartFile file) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), UTF_8))) {
            String csvHeader = bufferedReader.readLine();
            final String formattedHeader = getFormattedHeader(csvHeader);

            StatsType csvType = StatsType.findByCsvHeader(formattedHeader)
                    .orElseThrow(() -> new UnsupportedOperationException("Header is not supported: " + formattedHeader));

            StatsCsvImporter csvDataImporter = statsCsvImporterFactory.getCsvDataImporter(csvType);

            String tempFilePath = TempFileUtil.saveMultipartFileToTempFile(file, "csv-stats-", ".csv");

            final FileImportDto fileImportStatus = FileImportDto.builder()
                    .importDate(LocalDateTime.now())
                    .source(file.getOriginalFilename())
                    .status(FileImport.Status.UPLOADED)
                    .type(csvType)
                    .build();

            final FileImportDto uploadedFile = fileImportService.save(fileImportStatus);

            csvDataImporter.importFile(tempFilePath, uploadedFile.getId());
            return uploadedFile;
        }
    }

    @NotNull
    private String getFormattedHeader(String csvHeader) {
        if (Objects.isNull(csvHeader)) {
            throw new IllegalArgumentException("Header cannot be null");
        }

        return csvHeader.replaceAll("[\uFEFF-\uFFFF]", "").trim();
    }
}
