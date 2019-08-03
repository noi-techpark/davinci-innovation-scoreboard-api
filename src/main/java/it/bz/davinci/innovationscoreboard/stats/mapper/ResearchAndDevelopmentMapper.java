package it.bz.davinci.innovationscoreboard.stats.mapper;

import it.bz.davinci.innovationscoreboard.stats.csv.ResearchAndDevelopmentCsv;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEs;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ResearchAndDevelopmentMapper {

    ResearchAndDevelopmentMapper INSTANCE = Mappers.getMapper(ResearchAndDevelopmentMapper.class);

    public ResearchAndDevelopmentEs toEs(ResearchAndDevelopmentCsv researchAndDevelopmentCsv);
}
