package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.jpa.FileImportRepository;
import it.bz.davinci.innovationscoreboard.stats.mapper.FileImportMapper;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import it.bz.davinci.innovationscoreboard.stats.dto.UploadHistoryResponseDto;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
}
