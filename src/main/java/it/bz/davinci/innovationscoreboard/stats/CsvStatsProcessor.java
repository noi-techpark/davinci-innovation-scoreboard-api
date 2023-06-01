// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporter;
import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporterFactory;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportLogDto;
import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static it.bz.davinci.innovationscoreboard.stats.model.FileImport.Status.PROCESSING;

@Service
@AllArgsConstructor
public class CsvStatsProcessor {

    private final StatsCsvImporterFactory statsCsvImporterFactory;
    private final FileImportLogService fileImportLogService;

    @Async
    @Transactional
    public void process(String fileName, int fileImportId, StatsType statsType) {
        FileImportLogDto fileImportState = fileImportLogService.getById(fileImportId);
        fileImportState.setStatus(PROCESSING);
        fileImportLogService.save(fileImportState);

        StatsCsvImporter csvDataImporter = statsCsvImporterFactory.getCsvDataImporter(statsType);

        csvDataImporter.importFile(fileName, fileImportId);
    }

}
