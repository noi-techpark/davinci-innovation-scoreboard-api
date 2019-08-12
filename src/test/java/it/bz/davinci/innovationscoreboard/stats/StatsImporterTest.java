package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.csv.*;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StatsImporterTest {

    @Mock
    private ResearchAndDevelopmentCsvImporter researchAndDevelopmentDataImporter;

    @Mock
    private InnovationCsvImporter innovationCsvDataImporter;

    @Mock
    private EmploymentDemographicCsvImporter employmentDemographicCsvDataImporter;

    @Mock
    private FileImportService fileImportService;

    private StatsImporter statsImporter;

    @Before
    public void setUp() {
        StatsCsvImporterFactory statsCsvImporterFactory = new StatsCsvImporterFactory(researchAndDevelopmentDataImporter, innovationCsvDataImporter, employmentDemographicCsvDataImporter);
        statsImporter = new StatsImporter(statsCsvImporterFactory, fileImportService);
    }


    @Test
    public void givenSupportedFile_startUpload() throws IOException {

        when(fileImportService.save(any())).thenReturn(FileImportDto.builder().id(1).build());
        MultipartFile multipartFile = new MockMultipartFile("validResearchAndDevelopment2.csv", new FileInputStream(new File("src/test/resources/csv/validResearchAndDevelopment2.csv")));
        statsImporter.importFile(multipartFile);

        verify(researchAndDevelopmentDataImporter, times(1)).importFile(anyString(), anyInt());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenUnsupportedFile_throwException() throws IOException {

        MultipartFile multipartFile = new MockMultipartFile("invalidResearchAndDevelopment.csv", new FileInputStream(new File("src/test/resources/csv/invalidResearchAndDevelopment.csv")));
        statsImporter.importFile(multipartFile);
    }
}