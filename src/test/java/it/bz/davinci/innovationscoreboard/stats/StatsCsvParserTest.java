package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.csv.ResearchAndDevelopmentCsv;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StatsCsvParserTest {

    private final StatsCsvParser<ResearchAndDevelopmentCsv> researchAndDevelopmentStatsCsvParser = new StatsCsvParser<>(ResearchAndDevelopmentCsv.class);


    @Test
    public void successfulParsingByType() throws IOException {

        MultipartFile multipartFile = new MockMultipartFile("test.csv", new FileInputStream(new File("src/test/resources/csv/test.csv")));


        researchAndDevelopmentStatsCsvParser.parse(multipartFile);

    }

    @Test
    public void unsuccessfulParsingByType() throws IOException {

        MultipartFile multipartFile = new MockMultipartFile("test2.csv", new FileInputStream(new File("src/test/resources/csv/test.csv")));


        researchAndDevelopmentStatsCsvParser.parse(multipartFile);

    }
}