// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.dto.FileImportLogDto;
import it.bz.davinci.innovationscoreboard.stats.events.StatsCsvIndexedEvent;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static it.bz.davinci.innovationscoreboard.stats.model.FileImport.Status.REPLACED;

@Service
@AllArgsConstructor
public class ReplacePreviousUploadedFiles {

    private FileImportLogService fileImportLogService;

    @Async
    @Transactional
    @TransactionalEventListener
    public void onStatsCsvIndexed(StatsCsvIndexedEvent event) {
        final Integer fileImportId = event.getFileImportId();
        replacePreviouslyUploadedFilesStatus(fileImportId);
    }

    public void replacePreviouslyUploadedFilesStatus(Integer fileImportId) {
        final FileImportLogDto fileImportLogDto = fileImportLogService.getById(fileImportId);
        final List<FileImportLogDto> allByType = fileImportLogService.findAllByType(fileImportLogDto.getType());

        final List<FileImportLogDto> previousUploadsWithoutCurrentOne = allByType.stream()
                .filter(log -> !log.getId().equals(fileImportLogDto.getId()) && !REPLACED.equals(log.getStatus()))
                .collect(Collectors.toList());

        previousUploadsWithoutCurrentOne.forEach(log -> {
            log.setStatus(REPLACED);
            fileImportLogService.save(log);
        });
    }

}
