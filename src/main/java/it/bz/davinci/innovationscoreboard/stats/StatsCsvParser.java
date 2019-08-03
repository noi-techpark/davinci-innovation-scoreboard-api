package it.bz.davinci.innovationscoreboard.stats;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class StatsCsvParser {

    public List<Stats> parse(MultipartFile file) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            List<Stats> stats = new CsvToBeanBuilder<Stats>(reader)
                    .withSeparator('|').withType(Stats.class).build().parse();

            return stats;
        }
    }

}
