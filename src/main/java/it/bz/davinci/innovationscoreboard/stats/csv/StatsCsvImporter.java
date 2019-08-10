package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.CsvMapper;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Slf4j
public class StatsCsvImporter<CSV, ES> {

    private final FileImportService fileImportService;
    private final EsDao<ES> esDao;
    private final StatsCsvParser<CSV> statsCsvParser;
    private final CsvMapper<CSV, ES> mapper;

    public StatsCsvImporter(FileImportService fileImportService, EsDao<ES> esDao, Class<CSV> typeParameterClass, CsvMapper<CSV, ES> mapper) {
        this.fileImportService = fileImportService;
        this.esDao = esDao;
        this.statsCsvParser = new StatsCsvParser<>(typeParameterClass);
        this.mapper = mapper;
    }

    @Async
    @Transactional
    public void importFile(MultipartFile file, int fileImportId) {
        FileImportDto fileImportState = fileImportService.getById(fileImportId);
        fileImportState.setStatus(FileImport.Status.PROCESSING);
        fileImportState = fileImportService.save(fileImportState);
        try {
            List<CSV> data = statsCsvParser.parse(file);

            boolean indexCleaned = esDao.cleanIndex();

            if (indexCleaned) {
                data.forEach(record -> esDao.index(mapper.toEs(record)));
            } else {
                log.error("Failed to clean index for file: " + file.getName());
                fileImportState.setStatus(FileImport.Status.ERROR);
                fileImportService.save(fileImportState);
            }

            fileImportState.setStatus(FileImport.Status.SUCCESS);
            fileImportService.save(fileImportState);
        } catch (Exception e) {
            log.error("Failed to import stats for file: " + file.getName(), e);
            fileImportState.setStatus(FileImport.Status.ERROR);
            fileImportService.save(fileImportState);
        }
    }

}
