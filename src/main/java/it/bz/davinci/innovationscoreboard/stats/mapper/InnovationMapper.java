package it.bz.davinci.innovationscoreboard.stats.mapper;

import it.bz.davinci.innovationscoreboard.stats.csv.InnovationCsv;
import it.bz.davinci.innovationscoreboard.stats.es.InnovationEs;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InnovationMapper extends CsvMapper<InnovationCsv, InnovationEs> {

    InnovationMapper INSTANCE = Mappers.getMapper(InnovationMapper.class);

    @Mappings({})
    @Override
    InnovationEs toEs(InnovationCsv innovationCsv);
}
