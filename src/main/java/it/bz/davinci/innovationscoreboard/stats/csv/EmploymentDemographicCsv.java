package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDemographicCsv {

    public static final String SUPPORTED_HEADER = "\"ITTER107\",\"Territory\",\"VARICT\",\"Data type\",\"ATECO_2007\",\"NACE 2007\",\"CLLVT\",\"Size classes of persons employed\",\"TIME\",\"Select time\",\"Value\",\"Flag Codes\",\"Flags\"";

    @CsvBindByName(column = "ITTER107")
    private String ITTER107;

    @CsvBindByName(column = "Territory")
    private String territory;

    @CsvBindByName(column = "VARICT")
    private String VARICT;

    @CsvBindByName(column = "Data type")
    private String dataType;

    @CsvBindByName(column = "ATECO_2007")
    private String ATECO_2007;

    @CsvBindByName(column = "NACE 2007")
    private String NACE_2007;

    @CsvBindByName(column = "CLLVT")
    private String CLLVT;

    @CsvBindByName(column = "Size classes of persons employed")
    private String sizeClassesOfPersonsEmployed;

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
