package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ParserResult<T> {
    private List<T> data;
    private List<CsvException> exceptions;
}
