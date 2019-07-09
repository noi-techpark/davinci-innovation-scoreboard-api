package it.bz.davinci.innovationscoreboard.stats;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class StatsCsvParser {

    public void parse(MultipartFile file) throws IOException {

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println("first name : " + nextRecord[0]);
                System.out.println("last name : " + nextRecord[1]);
                System.out.println("==========================");
            }

        }

    }

}
