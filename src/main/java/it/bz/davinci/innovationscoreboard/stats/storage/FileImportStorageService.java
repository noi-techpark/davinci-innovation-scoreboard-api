package it.bz.davinci.innovationscoreboard.stats.storage;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
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
    private final FileImportService fileImportService;

    @Async
    @Transactional
    @TransactionalEventListener
    public void onStatsCsvIndexed(StatsCsvIndexedEvent event) {
        String s3FileName = fileImportStorageS3.upload(event.getFileName());

        final FileImportDto fileImportDto = fileImportService.getById(event.getFileImportId());
        fileImportDto.setExternalStorageLocation(s3FileName);
        fileImportService.save(fileImportDto);
    }

    public InMemoryFile download(Integer fileImportId) {
        final FileImportDto fileImportDto = fileImportService.getById(fileImportId);
        if (isNull(fileImportDto.getExternalStorageLocation())) {
            throw new NoLinkToExternalStorageFoundException("The requested import file is not stored on an external storage.");
        }
        return fileImportStorageS3.download(fileImportDto.getExternalStorageLocation());
    }

}
