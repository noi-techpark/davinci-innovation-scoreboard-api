package it.bz.davinci.innovationscoreboard.stats;

import com.google.common.collect.ImmutableMap;
import it.bz.davinci.innovationscoreboard.stats.csv.EmploymentDemographicCsv;
import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvParser;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseDto;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseGroupDto;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponsePerYearDto;
import it.bz.davinci.innovationscoreboard.stats.es.EmploymentDemographicEs;
import it.bz.davinci.innovationscoreboard.stats.es.EmploymentDemographicEsDao;
import it.bz.davinci.innovationscoreboard.stats.mapper.EmploymentDemographicMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnterprisesWithInnovationAggregatorTest {

    private final StatsCsvParser<EmploymentDemographicCsv> csvParser = new StatsCsvParser<>(EmploymentDemographicCsv.class);

    @Mock
    private EmploymentDemographicEsDao employmentDemographicEsDao;

    private EnterprisesWithInnovationAggregator enterprisesWithInnovationAggregator;

    @Before
    public void setup() {
        enterprisesWithInnovationAggregator = new EnterprisesWithInnovationAggregator(employmentDemographicEsDao);
    }

    @Test
    public void givenValidEnterprisesWithInnovationActivitiesDividedByTerritory_returnGroupedData() throws IOException {
        when(employmentDemographicEsDao.getEnterprisesWithInnovationActivitiesDividedByTerritory())
                .thenReturn(generateData("src/test/resources/csv/employment-demographic/enterprises-with-innovation-activities-divided-by-territory.csv"));

        final StatisticsResponseDto result = enterprisesWithInnovationAggregator.getEnterprisesWithInnovationActivitiesDividedByTerritory();

        final StatisticsResponsePerYearDto it2016 = result.getStatistics().get("IT").stream()
                .filter(entry -> entry.getYear().equals("2016"))
                .findFirst()
                .get();

        assertThat(it2016.getTotal(), equalTo(BigDecimal.valueOf(76895)));
        assertThat(it2016.getGroups(), contains(StatisticsResponseGroupDto.builder()
                .id("FORMA_INNOVAZ")
                .values(ImmutableMap.of("PTCON", BigDecimal.valueOf(19268), "OMKON", BigDecimal.valueOf(16757), "PTCOMK", BigDecimal.valueOf(40870)))
                .build()
        ));
    }

    private List<EmploymentDemographicEs> generateData(String path) throws IOException {
        File file = new File(path);
        final List<EmploymentDemographicCsv> data = csvParser.parse(file);
        return data.stream().map(EmploymentDemographicMapper.INSTANCE::toEs).collect(Collectors.toList());
    }
}