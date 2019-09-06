package it.bz.davinci.innovationscoreboard.stats.es;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InnovationInCompaniesWithAtLeast10EmployeesEs {
    @JsonProperty("ITTER107")
    private String ITTER107;
    private String territory;
    @JsonProperty("TIPO_DATO_CIS")
    private String TIPO_DATO_CIS;
    private String indicators;
    @JsonProperty("ATECO_2007")
    private String ATECO_2007;
    @JsonProperty("NACE_2007")
    private String NACE_2007;
    @JsonProperty("CLLVT")
    private String CLLVT;
    private String sizeClassesOfPersonsEmployed;
    @JsonProperty("FORMA_INNOVAZ")
    private String FORMA_INNOVAZ;
    private String informationOnTheInnovativeEnterprises;
    @JsonProperty("OBIETTIVI")
    private String OBIETTIVI;
    private String enterprisesGoals;
    @JsonProperty("STRATEGIE")
    private String STRATEGIE;
    private String enterprisesStrategies;
    @JsonProperty("OSTACOLI")
    private String OSTACOLI;
    private String obsacles;
    @JsonProperty("ORD_LIVELLO")
    private String ORD_LIVELLO;
    private String degreeOfImportance;
    @JsonProperty("TIME")
    private String TIME;
    private String selectTime;
    private BigDecimal value;
    private String flagCodes;
    private String flags;
}
