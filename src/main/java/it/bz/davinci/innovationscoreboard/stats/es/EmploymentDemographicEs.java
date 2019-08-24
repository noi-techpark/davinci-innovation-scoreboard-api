package it.bz.davinci.innovationscoreboard.stats.es;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDemographicEs {
    private String ITTER107;
    private String territory;
    private String TIPO_DATO_CIS;
    private String indicators;
    private String ATECO_2007;
    private String NACE_2007;
    private String CLLVT;
    private String sizeClassesOfPersonsEmployed;
    private String FORMA_INNOVAZ;
    private String informationOnTheInnovativeEnterprises;
    private String OBIETTIVI;
    private String enterprisesGoals;
    private String STRATEGIE;
    private String enterprisesStrategies;
    private String OSTACOLI;
    private String obsacles;
    private String ORD_LIVELLO;
    private String degreeOfImportance;
    private String TIME;
    private String selectTime;
    private Integer value;
    private String flagCodes;
    private String flags;
}
