package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;


public class StatsCsvParser<T> {

    private final Class<T> typeParameterClass;

    public StatsCsvParser(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public List<T> parse(File file) throws IOException {
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            List<T> stats = new CsvToBeanBuilder<T>(reader)
                    .withSeparator('|')
                    .withType(typeParameterClass)
                    .build()
                    .parse();

            return stats;
        }
    }

}
