package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.exceptions.CsvException;
import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.events.StatsCsvIndexedEvent;
import it.bz.davinci.innovationscoreboard.stats.mapper.CsvMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

import static it.bz.davinci.innovationscoreboard.stats.model.FileImport.Status.*;

@Slf4j
public class StatsCsvImporter<CSV, ES> {

    private final FileImportService fileImportService;
    private final EsDao<ES> esDao;
    private final StatsCsvParser<CSV> statsCsvParser;
    private final CsvMapper<CSV, ES> mapper;
    private final ApplicationEventPublisher publisher;

    public StatsCsvImporter(FileImportService fileImportService, EsDao<ES> esDao, Class<CSV> typeParameterClass, CsvMapper<CSV, ES> mapper, ApplicationEventPublisher publisher) {
        this.fileImportService = fileImportService;
        this.esDao = esDao;
        this.statsCsvParser = new StatsCsvParser<>(typeParameterClass);
        this.mapper = mapper;
        this.publisher = publisher;
    }

    @Async
    @Transactional
    public void importFile(String fileName, int fileImportId) {
        FileImportDto fileImportState = fileImportService.getById(fileImportId);
        fileImportState.setStatus(PROCESSING);
        fileImportState = fileImportService.save(fileImportState);

        try {
            File file = new File(fileName);
            final ParserResult<CSV> parserResult = statsCsvParser.parse(file);

            final List<CSV> data = parserResult.getData();
            final List<CsvException> exceptions = parserResult.getExceptions();

            if (data.isEmpty() && !exceptions.isEmpty()) {
                log.error("Failed to parse all rows for file: " + fileImportState.getSource());
                fileImportState.setStatus(PROCESSED_WITH_ERRORS);
                fileImportState.setLogs(parserResult.getExceptionLog());
            } else {
                boolean indexCleaned = esDao.cleanIndex();

                if (indexCleaned) {
                    parserResult.getData().forEach(record -> esDao.index(mapper.toEs(record)));

                    if (parserResult.getExceptions().isEmpty()) {
                        fileImportState.setStatus(PROCESSED_WITH_SUCCESS);
                    } else {
                        fileImportState.setStatus(PROCESSED_WITH_WARNINGS);
                        fileImportState.setLogs(parserResult.getExceptionLog());
                    }
                } else {
                    log.error("Failed to clean index for file: " + fileImportState.getSource());
                    fileImportState.setStatus(PROCESSED_WITH_ERRORS);
                    fileImportState.setLogs("Failed to delete index for file: " + fileImportState.getSource());
                }
            }

        } catch (Exception e) {
            log.error("Failed to import stats for file: " + fileImportState.getSource(), e);
            fileImportState.setStatus(PROCESSED_WITH_ERRORS);
            fileImportState.setLogs("Failed to open temp file: " + fileImportState.getSource());
        }

        fileImportService.save(fileImportState);
        publisher.publishEvent(new StatsCsvIndexedEvent(fileName));
    }

}
