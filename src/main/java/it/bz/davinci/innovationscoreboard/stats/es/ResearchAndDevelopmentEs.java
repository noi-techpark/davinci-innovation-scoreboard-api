package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResearchAndDevelopmentEs {
    @JsonProperty("ITTER107")
    private String ITTER107;
    private String Territory;
    @JsonProperty("TIPO_DATO_CIS")
    private String TIPO_DATO_CIS;
    private String dataType;
    @JsonProperty("SETTISTSEC2010")
    private String SETTISTSEC2010;
    private String institutionalSector;
    @JsonProperty("ATECO_2007")
    private String ATECO_2007;
    private String sectorOfEconomicActivity;
    @JsonProperty("CLLVT")
    private String CLLVT;
    private String numberOfEmployees;
    @JsonProperty("ATTIVEC_CSC")
    private String ATTIVEC_CSC;
    private String typeOfResearchAndDevelopment;
    @JsonProperty("CORSO_LAUREA")
    private String CORSO_LAUREA;
    private String fieldOfScience;
    @JsonProperty("ATECO_2007_B")
    private String ATECO_2007_B;
    private String groupsOfProductsServicesInvolvedByTheResearch;
    @JsonProperty("SETTISTSEC2010_B")
    private String SETTISTSEC2010_B;
    private String sourceOfFunds;
    @JsonProperty("TIPENTSPE")
    private String TIPENTSPE;
    private String typeOfCosts;
    @JsonProperty("OBIETTIVI")
    private String OBIETTIVI;
    private String socialAndEconomicGoals;
    @JsonProperty("PROFILO_PROF")
    private String PROFILO_PROF;
    private String professionalStatus;
    @JsonProperty("SEXISTAT1")
    private String SEXISTAT1;
    private String gender;
    @JsonProperty("TITOLO_STUDIO")
    private String TITOLO_STUDIO;
    private String qualificationISCED;
    @JsonProperty("TIME")
    private String TIME;
    private String selectTime;
    private BigDecimal value;
    private String flagCodes;
    private String flags;
}
