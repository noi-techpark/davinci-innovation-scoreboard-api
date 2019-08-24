package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResearchAndDevelopmentCsv {

    public static final String SUPPORTED_HEADER = "\"ITTER107\"|\"Territory\"|\"TIPO_DATO_CIS\"|\"Data type\"|\"SETTISTSEC2010\"|\"Institutional sector\"|\"ATECO_2007\"|\"Sector of economic activity\"|\"CLLVT\"|\"Number of employees\"|\"ATTIVEC_CSC\"|\"Type of Research and Development\"|\"CORSO_LAUREA\"|\"Field of science\"|\"ATECO_2007_B\"|\"Groups of products / services involved by the research\"|\"SETTISTSEC2010_B\"|\"Source of funds\"|\"TIPENTSPE\"|\"Type of costs\"|\"OBIETTIVI\"|\"Social and economic goals\"|\"PROFILO_PROF\"|\"Professional status\"|\"SEXISTAT1\"|\"Gender\"|\"TITOLO_STUDIO\"|\"Qualification - ISCED\"|\"TIME\"|\"Select time\"|\"Value\"|\"Flag Codes\"|\"Flags\"";

    @CsvBindByName(column = "ITTER107")
    private String ITTER107;
    @CsvBindByName(column = "Territory")
    private String Territory;
    @CsvBindByName(column = "TIPO_DATO_CIS")
    private String TIPO_DATO_CIS;
    @CsvBindByName(column = "Data type")
    private String dataType;
    @CsvBindByName(column = "SETTISTSEC2010")
    private String SETTISTSEC2010;
    @CsvBindByName(column = "Institutional sector")
    private String institutionalSector;
    @CsvBindByName(column = "ATECO_2007")
    private String ATECO_2007;
    @CsvBindByName(column = "Sector of economic activity")
    private String sectorOfEconomicActivity;
    @CsvBindByName(column = "CLLVT")
    private String CLLVT;
    @CsvBindByName(column = "Number of employees")
    private String numberOfEmployees;
    @CsvBindByName(column = "ATTIVEC_CSC")
    private String ATTIVEC_CSC;
    @CsvBindByName(column = "Type of Research and Development")
    private String typeOfResearchAndDevelopment;
    @CsvBindByName(column = "CORSO_LAUREA")
    private String CORSO_LAUREA;
    @CsvBindByName(column = "Field of science")
    private String fieldOfScience;
    @CsvBindByName(column = "ATECO_2007_B")
    private String ATECO_2007_B;
    @CsvBindByName(column = "Groups of products / services involved by the research")
    private String groupsOfProductsServicesInvolvedByTheResearch;
    @CsvBindByName(column = "SETTISTSEC2010_B")
    private String SETTISTSEC2010_B;
    @CsvBindByName(column = "Source of funds")
    private String sourceOfFunds;
    @CsvBindByName(column = "TIPENTSPE")
    private String TIPENTSPE;
    @CsvBindByName(column = "Type of costs")
    private String typeOfCosts;
    @CsvBindByName(column = "OBIETTIVI")
    private String OBIETTIVI;
    @CsvBindByName(column = "Social and economic goals")
    private String socialAndEconomicGoals;
    @CsvBindByName(column = "PROFILO_PROF")
    private String PROFILO_PROF;
    @CsvBindByName(column = "Professional status")
    private String professionalStatus;
    @CsvBindByName(column = "SEXISTAT1")
    private String SEXISTAT1;
    @CsvBindByName(column = "Gender")
    private String gender;
    @CsvBindByName(column = "TITOLO_STUDIO")
    private String TITOLO_STUDIO;
    @CsvBindByName(column = "Qualification - ISCED")
    private String qualificationISCED;
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
