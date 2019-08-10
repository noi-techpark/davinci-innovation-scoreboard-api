package it.bz.davinci.innovationscoreboard.stats.mapper;

import it.bz.davinci.innovationscoreboard.stats.csv.EmploymentDemographicCsv;
import it.bz.davinci.innovationscoreboard.stats.es.EmploymentDemographicEs;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmploymentDemographicMapper extends CsvMapper<EmploymentDemographicCsv, EmploymentDemographicEs> {

    EmploymentDemographicMapper INSTANCE = Mappers.getMapper(EmploymentDemographicMapper.class);

    @Mappings({})
    @Override
    EmploymentDemographicEs toEs(EmploymentDemographicCsv employmentDemographicCsv);
}
