package it.bz.davinci.innovationscoreboard.stats.rest;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.StatsImporter;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import it.bz.davinci.innovationscoreboard.stats.dto.UploadHistoryResponseDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "v1/stats")
public class StatsUploadController {

    private final StatsImporter statsService;
    private final FileImportService fileImportService;

    public StatsUploadController(StatsImporter statsService, FileImportService fileImportService) {
        this.statsService = statsService;
        this.fileImportService = fileImportService;
    }

    @PostMapping(value = "/upload/csv")
    public FileImportDto upload(@RequestParam("file") MultipartFile file) throws IOException {
        return statsService.importFile(file);
    }

    @GetMapping(value = "/upload/history")
    public UploadHistoryResponseDto getHistory() {
        return fileImportService.findAll();
    }

}
