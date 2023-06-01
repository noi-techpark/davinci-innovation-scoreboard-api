// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportLogService;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEs;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.ResearchAndDevelopmentMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ResearchAndDevelopmentCsvImporter extends StatsCsvImporter<ResearchAndDevelopmentCsv, ResearchAndDevelopmentEs> {
    public ResearchAndDevelopmentCsvImporter(FileImportLogService fileImportLogService, ResearchAndDevelopmentEsDao esDao, ApplicationEventPublisher publisher) {
        super(fileImportLogService, esDao, ResearchAndDevelopmentCsv.class, ResearchAndDevelopmentMapper.INSTANCE, publisher);
    }
}
