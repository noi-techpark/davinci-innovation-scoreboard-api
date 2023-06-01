// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Data
public class ParserResult<T> {
    private List<T> data;
    private List<CsvException> exceptions;

    public String getExceptionLog() {
        if (isNull(exceptions)) {
            return null;
        }

        return exceptions.stream()
                .map(exception -> "Line " + exception.getLineNumber() + ": " + exception.getMessage() + "\n")
                .reduce("", String::concat);
    }
}
