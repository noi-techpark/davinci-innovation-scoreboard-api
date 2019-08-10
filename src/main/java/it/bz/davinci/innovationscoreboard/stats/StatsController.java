package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.dto.UploadHistoryResponseDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1/stats")
public class StatsController {

    private final StatsImporter statsService;
    private final UploadHistoryService uploadHistoryService;

    public StatsController(StatsImporter statsService, UploadHistoryService uploadHistoryService) {
        this.statsService = statsService;
        this.uploadHistoryService = uploadHistoryService;
    }

    @PostMapping(value = "/upload/csv")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        statsService.importFile(file);
    }

    @GetMapping(value = "/upload/history")
    public UploadHistoryResponseDto getHistory() {
        return uploadHistoryService.getHistory();
    }

}
