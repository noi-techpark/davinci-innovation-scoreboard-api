package it.bz.davinci.innovationscoreboard.stats.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class FileImport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String source;
    private LocalDateTime importDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        UPLOADED, PROCESSING, SUCCESS, ERROR
    }

    @CreationTimestamp
    private Timestamp creation;

    @UpdateTimestamp
    private Timestamp lastUpdate;
}
