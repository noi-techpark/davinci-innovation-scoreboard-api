package it.bz.davinci.innovationscoreboard.stats.storage;

import it.bz.davinci.innovationscoreboard.stats.FileImportLogService;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportLogDto;
import it.bz.davinci.innovationscoreboard.stats.events.StatsCsvIndexedEvent;
import it.bz.davinci.innovationscoreboard.utils.io.InMemoryFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
public class FileImportStorageService {

    private final FileImportStorageS3 fileImportStorageS3;
    private final FileImportLogService fileImportLogService;

    @Async
    @Transactional
    @TransactionalEventListener
    public void onStatsCsvIndexed(StatsCsvIndexedEvent event) {
        String s3FileName = fileImportStorageS3.upload(event.getFileName());

        final FileImportLogDto fileImportLogDto = fileImportLogService.getById(event.getFileImportId());
        fileImportLogDto.setExternalStorageLocation(s3FileName);
        fileImportLogService.save(fileImportLogDto);
    }

    public InMemoryFile download(Integer fileImportId) {
        final FileImportLogDto fileImportLogDto = fileImportLogService.getById(fileImportId);
        if (isNull(fileImportLogDto.getExternalStorageLocation())) {
            throw new NoLinkToExternalStorageFoundException("The requested import file is not stored on an external storage.");
        }
        return fileImportStorageS3.download(fileImportLogDto.getExternalStorageLocation());
    }

}
