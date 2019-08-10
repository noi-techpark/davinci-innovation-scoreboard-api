package it.bz.davinci.innovationscoreboard.stats.jpa;

import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileImportRepository extends JpaRepository<FileImport, Integer> {
}
