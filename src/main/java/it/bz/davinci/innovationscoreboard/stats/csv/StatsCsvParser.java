// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.util.List;
import java.util.Locale;


public class StatsCsvParser<T> {

    private final Class<T> typeParameterClass;

    public StatsCsvParser(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public ParserResult<T> parse(File file) throws IOException {
        try (Reader reader = new InputStreamReader(new BOMInputStream(new FileInputStream(file)))) {
            final CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withSeparator('|')
                    .withType(typeParameterClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withThrowExceptions(false)
                    .withErrorLocale(Locale.ENGLISH)
                    .build();

            final List<T> stats = csvToBean.parse();
            final List<CsvException> capturedExceptions = csvToBean.getCapturedExceptions();

            return new ParserResult<>(stats, capturedExceptions);
        }
    }

}
