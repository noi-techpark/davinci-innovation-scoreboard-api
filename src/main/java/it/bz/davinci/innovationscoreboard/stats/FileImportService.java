package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.jpa.FileImportRepository;
import it.bz.davinci.innovationscoreboard.stats.mapper.FileImportMapper;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import it.bz.davinci.innovationscoreboard.stats.dto.UploadHistoryResponseDto;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileImportService {

    private final FileImportRepository fileImportRepository;

    public UploadHistoryResponseDto findAll() {
        List<FileImportDto> imports = fileImportRepository.findAll().stream()
                .map(FileImportMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

        return new UploadHistoryResponseDto(imports);
    }

    public FileImportDto save(FileImportDto fileImportDto) {
        FileImport fileImport;
        if (Objects.isNull(fileImportDto.getId())) {
            fileImport = new FileImport();
        } else {
            fileImport = fileImportRepository.getOne(fileImportDto.getId());
        }

        fileImport.setImportDate(fileImportDto.getImportDate());
        fileImport.setSource(fileImportDto.getSource());
        fileImport.setStatus(fileImportDto.getStatus());
        fileImport.setLogs(fileImportDto.getLogs());

        return FileImportMapper.INSTANCE.toDto(fileImportRepository.save(fileImport));
    }

    public FileImportDto getById(int id) {
        final FileImport result = fileImportRepository.getOne(id);
        return FileImportMapper.INSTANCE.toDto(result);
    }
}
