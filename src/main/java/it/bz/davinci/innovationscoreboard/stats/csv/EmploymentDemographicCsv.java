package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDemographicCsv {
    @CsvBindByName(column = "ITTER107")
    private String ITTER107;
    @CsvBindByName(column = "Territory")
    private String territory;
    @CsvBindByName(column = "TIPO_DATO_CIS")
    private String TIPO_DATO_CIS;
    @CsvBindByName(column = "Indicators")
    private String indicators;
    @CsvBindByName(column = "ATECO_2007")
    private String ATECO_2007;
    @CsvBindByName(column = "NACE 2007")
    private String NACE_2007;
    @CsvBindByName(column = "CLLVT")
    private String CLLVT;
    @CsvBindByName(column = "Size classes of persons employed")
    private String sizeClassesOfPersonsEmployed;
    @CsvBindByName(column = "FORMA_INNOVAZ")
    private String FORMA_INNOVAZ;
    @CsvBindByName(column = "Information on the innovative enterprises")
    private String informationOnTheInnovativeEnterprises;
    @CsvBindByName(column = "OBIETTIVI")
    private String OBIETTIVI;
    @CsvBindByName(column = "Enterprises goals")
    private String enterprisesGoals;
    @CsvBindByName(column = "STRATEGIE")
    private String STRATEGIE;
    @CsvBindByName(column = "Enterprises strategies")
    private String enterprisesStrategies;
    @CsvBindByName(column = "OSTACOLI")
    private String OSTACOLI;
    @CsvBindByName(column = "Obsacles")
    private String obsacles;
    @CsvBindByName(column = "ORD_LIVELLO")
    private String ORD_LIVELLO;
    @CsvBindByName(column = "Degree of importance")
    private String degreeOfImportance;
    @CsvBindByName(column = "TIME")
    private String TIME;
    @CsvBindByName(column = "Select time")
    private String selectTime;
    @CsvBindByName(column = "Value")
    private BigDecimal value;
    @CsvBindByName(column = "Flag Codes")
    private String flagCodes;
    @CsvBindByName(column = "Flags")
    private String flags;

}
