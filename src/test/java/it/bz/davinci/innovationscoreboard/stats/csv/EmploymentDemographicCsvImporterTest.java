package it.bz.davinci.innovationscoreboard.stats.csv;

import it.bz.davinci.innovationscoreboard.stats.FileImportService;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import it.bz.davinci.innovationscoreboard.stats.es.EmploymentDemographicEs;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.jpa.FileImportRepository;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({FileImportService.class})
public class EmploymentDemographicCsvImporterTest {

    private EmploymentDemographicCsvImporter employmentDemographicCsvImporter;

    @Autowired
    private FileImportRepository fileImportRepository;

    @Autowired
    private FileImportService fileImportService;

    @Mock
    private EsDao<EmploymentDemographicEs> esDao;

    @Before
    public void setup() {
        when(esDao.cleanIndex()).thenReturn(true);
        employmentDemographicCsvImporter = new EmploymentDemographicCsvImporter(fileImportService, esDao);
        fileImportRepository.deleteAll();
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldFailIfNoDBEntryIsPresent() {
        employmentDemographicCsvImporter.importFile("src/test/resources/csv/validResearchAndDevelopment2.csv", 1);
    }

    @Test
    public void shouldCleanAndUploadDataToIndex() throws IOException {
        MultipartFile multipartFile = createFile("validEmploymentDemographic.csv");
        final FileImportDto uploadedFile = fileImportService.save(FileImportDto.builder()
                .source(multipartFile.getName())
                .importDate(LocalDateTime.now())
                .status(FileImport.Status.UPLOADED).build());

        employmentDemographicCsvImporter.importFile("src/test/resources/csv/validEmploymentDemographic.csv", uploadedFile.getId());

        verify(esDao, times(2)).index(Mockito.any(EmploymentDemographicEs.class));
        final FileImportDto fileImport = fileImportService.getById(uploadedFile.getId());

        assertThat(fileImport.getStatus(), equalTo(FileImport.Status.PROCESSED_WITH_SUCCESS));
    }

    private MockMultipartFile createFile(String fileName) throws IOException {
        return new MockMultipartFile(fileName, new FileInputStream(new File("src/test/resources/csv/" + fileName)));
    }
}