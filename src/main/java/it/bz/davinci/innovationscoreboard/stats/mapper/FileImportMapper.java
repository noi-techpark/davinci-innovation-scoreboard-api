package it.bz.davinci.innovationscoreboard.stats.mapper;

import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FileImportMapper {

    FileImportMapper INSTANCE = Mappers.getMapper(FileImportMapper.class);

    @Mappings({})
    public FileImportDto toDto(FileImport fileImport);
}
