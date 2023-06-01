// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FileImport {

    public enum Status {
        UPLOADED, PROCESSING, PROCESSED_WITH_SUCCESS, PROCESSED_WITH_ERRORS, PROCESSED_WITH_WARNINGS, REPLACED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String source;
    private LocalDateTime importDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Timestamp creation;

    @UpdateTimestamp
    private Timestamp lastUpdate;

    private String logs;

    @Enumerated(EnumType.STRING)
    private StatsType type;

    private String externalStorageLocation;
}
