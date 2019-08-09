package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvDataImporter;
import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporterFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@AllArgsConstructor
public class StatsImporter {

    private final StatsCsvImporterFactory statsCsvImporterFactory;


    public void uploadFile(MultipartFile file) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), UTF_8))) {
            String csvHeader = bufferedReader.readLine();
            StatsCsvDataImporter csvDataImporter = statsCsvImporterFactory.getCsvDataImporter(csvHeader);
            csvDataImporter.run(file);
        }

    }


}
