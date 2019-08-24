package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class EsDaoTest {

    private final EsDao<EmploymentDemographicEs> esEsDao = new EmploymentDemographicEsDao(null, new ObjectMapper());

    @Test
    public void index() {
        EmploymentDemographicEs document = new EmploymentDemographicEs();
        document.setITTER107("IT");

        esEsDao.index(document);
    }
}