package it.bz.davinci.innovationscoreboard.stats.csv;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class StatsCsvImporterFactoryTest {

    private StatsCsvImporterFactory statsCsvImporterFactory;

    @Before
    public void setUp() {
        ResearchAndDevelopmentCsvDataImporter researchAndDevelopmentDataImporter = new ResearchAndDevelopmentCsvDataImporter(null);
        InnovationCsvDataImporter innovationCsvDataImporter = new InnovationCsvDataImporter(null);
        EmploymentDemographicCsvDataImporter employmentDemographicCsvDataImporter = new EmploymentDemographicCsvDataImporter(null);
        statsCsvImporterFactory = new StatsCsvImporterFactory(researchAndDevelopmentDataImporter, innovationCsvDataImporter, employmentDemographicCsvDataImporter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwErrorIfHeaderParameterIsNull() {
        statsCsvImporterFactory.getCsvDataImporter(null);
    }

    @Test
    public void returnsResearchAndDevelopmentDataImporter() {
        final StatsCsvDataImporter csvDataImporter = statsCsvImporterFactory.getCsvDataImporter(ResearchAndDevelopmentCsv.SUPPORTED_HEADER);
        assertThat(csvDataImporter, instanceOf(ResearchAndDevelopmentCsvDataImporter.class));
    }

    @Test
    public void returnsInnovationDataImporter() {
        final StatsCsvDataImporter csvDataImporter = statsCsvImporterFactory.getCsvDataImporter(InnovationCsv.SUPPORTED_HEADER);
        assertThat(csvDataImporter, instanceOf(InnovationCsvDataImporter.class));
    }
    @Test
    public void returnsEmploymentDemographicDataImporter() {
        final StatsCsvDataImporter csvDataImporter = statsCsvImporterFactory.getCsvDataImporter(EmploymentDemographicCsv.SUPPORTED_HEADER);
        assertThat(csvDataImporter, instanceOf(EmploymentDemographicCsvDataImporter.class));
    }


}