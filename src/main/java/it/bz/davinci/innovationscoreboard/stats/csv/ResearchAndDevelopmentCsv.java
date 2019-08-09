package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResearchAndDevelopmentCsv {

    public static final String SUPPORTED_HEADER = "\uFEFF\"ITTER107\",\"Territory\",\"TIPO_DATO_CIS\",\"Data type\",\"SETTISTSEC2010\",\"Institutional sector\",\"TIME\",\"Select time\",\"Value\",\"Flag Codes\",\"Flags\"";

    @CsvBindByName(column = "ITTER107")
    private String ITTER107;

    @CsvBindByName(column = "Territory")
    private String territory;

    @CsvBindByName(column = "TIPO_DATO_CIS")
    private String TIPO_DATO_CIS;

    @CsvBindByName(column = "Tipo dato")
    private String tipoDato;

    @CsvBindByName(column = "Data type")
    private String dataType;

    @CsvBindByName(column = "SETTISTSEC2010")
    private String SETTISTSEC2010;

    @CsvBindByName(column = "Institutional sector")
    private String institutionalSector;

    @CsvBindByName(column = "Settore di attività economica")
    private String settoreDiAttivitaEconomica;

    @CsvBindByName(column = "TIME")
    private String TIME;

    @CsvBindByName(column = "Select time")
    private String selectTime;

    @CsvBindByName(column = "Value")
    private String value;

    @CsvBindByName(column = "Flag Codes")
    private String flagCodes;

    @CsvBindByName(column = "Flags")
    private String flags;
}
