package it.bz.davinci.innovationscoreboard.stats.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class EsStats {
    private String firstName;
    private String lastName;
}
