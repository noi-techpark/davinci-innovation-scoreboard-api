package it.bz.davinci.innovationscoreboard.stats;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Stats {
    @CsvBindByName
    private String firstName;
    @CsvBindByName
    private String lastName;
}
