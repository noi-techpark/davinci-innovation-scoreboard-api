package it.bz.davinci.innovationscoreboard.stats.rest;

import it.bz.davinci.innovationscoreboard.stats.FileImportLogService;
import it.bz.davinci.innovationscoreboard.stats.CsvStatsUploader;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportLogDto;
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

    private final CsvStatsUploader statsService;
    private final FileImportLogService fileImportLogService;
    private final FileImportStorageService fileImportStorageService;
    private final RestResponseFactory restResponseFactory;

    public StatsUploadController(CsvStatsUploader statsService, FileImportLogService fileImportLogService, FileImportStorageService fileImportStorageService, RestResponseFactory restResponseFactory) {
        this.statsService = statsService;
        this.fileImportLogService = fileImportLogService;
        this.fileImportStorageService = fileImportStorageService;
        this.restResponseFactory = restResponseFactory;
    }

    @PostMapping(value = "/upload/csv")
    public FileImportLogDto upload(@RequestParam("file") MultipartFile file) throws IOException {
        return statsService.importFile(file);
    }

    @GetMapping(value = "/upload/history")
    public UploadHistoryResponseDto getHistory() {
        return fileImportLogService.findAll();
    }

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Integer id) {
        return restResponseFactory.createFileResponse(fileImportStorageService.download(id));
    }

}
