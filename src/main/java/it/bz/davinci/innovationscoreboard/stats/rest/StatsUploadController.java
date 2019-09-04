package it.bz.davinci.innovationscoreboard.stats.rest;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.StatsImporter;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import it.bz.davinci.innovationscoreboard.stats.dto.UploadHistoryResponseDto;
import it.bz.davinci.innovationscoreboard.stats.storage.FileImportStorageService;
import it.bz.davinci.innovationscoreboard.utils.rest.RestResponseFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "v1/stats")
public class StatsUploadController {

    private final StatsImporter statsService;
    private final FileImportService fileImportService;
    private final FileImportStorageService fileImportStorageService;
    private final RestResponseFactory restResponseFactory;

    public StatsUploadController(StatsImporter statsService, FileImportService fileImportService, FileImportStorageService fileImportStorageService, RestResponseFactory restResponseFactory) {
        this.statsService = statsService;
        this.fileImportService = fileImportService;
        this.fileImportStorageService = fileImportStorageService;
        this.restResponseFactory = restResponseFactory;
    }

    @PostMapping(value = "/upload/csv")
    public FileImportDto upload(@RequestParam("file") MultipartFile file) throws IOException {
        return statsService.importFile(file);
    }

    @GetMapping(value = "/upload/history")
    public UploadHistoryResponseDto getHistory() {
        return fileImportService.findAll();
    }

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Integer id) {
        return restResponseFactory.createFileResponse(fileImportStorageService.download(id));
    }

}
