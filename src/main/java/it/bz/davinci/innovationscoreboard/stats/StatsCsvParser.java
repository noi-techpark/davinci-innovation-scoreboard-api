package it.bz.davinci.innovationscoreboard.stats;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;


public class StatsCsvParser<T> {

    final Class<T> typeParameterClass;

    public StatsCsvParser(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public List<T> parse(MultipartFile file) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            List<T> stats = new CsvToBeanBuilder<T>(reader)
                    .withSeparator('|')
                    .withType(typeParameterClass)
                    .build()
                    .parse();

            return stats;
        }
    }

}
