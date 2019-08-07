package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEs;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatsImporterTest {


    @Autowired
    private StatsImporter statsImporter;

    @MockBean
    private ResearchAndDevelopmentEsDao researchAndDevelopmentEsDao;


    @Test
    public void givenSupportedFile_startUpload() throws IOException {

        when(researchAndDevelopmentEsDao.cleanIndex()).thenReturn(true);
        MultipartFile multipartFile = new MockMultipartFile("validResearchAndDevelopment.csv", new FileInputStream(new File("src/test/resources/csv/validResearchAndDevelopment.csv")));
        statsImporter.uploadFile(multipartFile);

        verify(researchAndDevelopmentEsDao, times(2)).index(any(ResearchAndDevelopmentEs.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenUnsupportedFile_throwException() throws IOException {

        MultipartFile multipartFile = new MockMultipartFile("invalidResearchAndDevelopment.csv", new FileInputStream(new File("src/test/resources/csv/invalidResearchAndDevelopment.csv")));
        statsImporter.uploadFile(multipartFile);
    }
}