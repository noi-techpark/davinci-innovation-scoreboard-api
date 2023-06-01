// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.dto.FileImportLogDto;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportResponseDto;
import it.bz.davinci.innovationscoreboard.stats.dto.UploadHistoryResponseDto;
import it.bz.davinci.innovationscoreboard.stats.jpa.FileImportRepository;
import it.bz.davinci.innovationscoreboard.stats.mapper.FileImportMapper;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileImportLogService {

    private final FileImportRepository fileImportRepository;

    public UploadHistoryResponseDto findAll() {
        List<FileImportResponseDto> imports = fileImportRepository.findAll().stream()
                .map(FileImportMapper.INSTANCE::toDto)
                .map(FileImportMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());

        return new UploadHistoryResponseDto(imports);
    }

    @NotNull
    public List<FileImportLogDto> findAllByType(@NotNull StatsType type) {
        return fileImportRepository.findAllByType(type).stream()
                .map(FileImportMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public FileImportLogDto save(FileImportLogDto fileImportLogDto) {
        FileImport fileImport;
        if (Objects.isNull(fileImportLogDto.getId())) {
            fileImport = new FileImport();
        } else {
            fileImport = fileImportRepository.getOne(fileImportLogDto.getId());
        }

        fileImport.setImportDate(fileImportLogDto.getImportDate());
        fileImport.setSource(fileImportLogDto.getSource());
        fileImport.setStatus(fileImportLogDto.getStatus());
        fileImport.setLogs(fileImportLogDto.getLogs());
        fileImport.setType(fileImportLogDto.getType());
        fileImport.setExternalStorageLocation(fileImportLogDto.getExternalStorageLocation());

        return FileImportMapper.INSTANCE.toDto(fileImportRepository.save(fileImport));
    }

    public FileImportLogDto getById(int id) {
        final FileImport result = fileImportRepository.getOne(id);
        return FileImportMapper.INSTANCE.toDto(result);
    }
}
