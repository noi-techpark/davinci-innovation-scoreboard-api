package it.bz.davinci.innovationscoreboard.stats.csv;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StatsCsvDataImporter {
    void run(MultipartFile file) throws IOException;
}
