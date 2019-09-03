package it.bz.davinci.innovationscoreboard.stats.model;

import java.util.Optional;

public enum StatsType {
    RESEARCH_AND_DEVELOPMENT("\"ITTER107\"|\"Territory\"|\"TIPO_DATO_CIS\"|\"Data type\"|\"SETTISTSEC2010\"|\"Institutional sector\"|\"ATECO_2007\"|\"Sector of economic activity\"|\"CLLVT\"|\"Number of employees\"|\"ATTIVEC_CSC\"|\"Type of Research and Development\"|\"CORSO_LAUREA\"|\"Field of science\"|\"ATECO_2007_B\"|\"Groups of products / services involved by the research\"|\"SETTISTSEC2010_B\"|\"Source of funds\"|\"TIPENTSPE\"|\"Type of costs\"|\"OBIETTIVI\"|\"Social and economic goals\"|\"PROFILO_PROF\"|\"Professional status\"|\"SEXISTAT1\"|\"Gender\"|\"TITOLO_STUDIO\"|\"Qualification - ISCED\"|\"TIME\"|\"Select time\"|\"Value\"|\"Flag Codes\"|\"Flags\""),
    INNOVATION_IN_COMPANIES_WITH_AT_LEAST_10_EMPLOYEES("\"ITTER107\"|\"Territory\"|\"VARICT\"|\"Data type\"|\"ATECO_2007\"|\"NACE 2007\"|\"CLLVT\"|\"Size classes of persons employed\"|\"TIME\"|\"Select time\"|\"Value\"|\"Flag Codes\"|\"Flags\""),
    ICT_IN_COMPANIES_WITH_AT_LEAST_10_EMPLOYEES("\"ITTER107\"|\"Territory\"|\"TIPO_DATO_CIS\"|\"Indicators\"|\"ATECO_2007\"|\"NACE 2007\"|\"CLLVT\"|\"Size classes of persons employed\"|\"FORMA_INNOVAZ\"|\"Information on the innovative enterprises\"|\"OBIETTIVI\"|\"Enterprises goals\"|\"STRATEGIE\"|\"Enterprises strategies\"|\"OSTACOLI\"|\"Obsacles\"|\"ORD_LIVELLO\"|\"Degree of importance\"|\"TIME\"|\"Select time\"|\"Value\"|\"Flag Codes\"|\"Flags\"");

    public final String csvHeader;

    StatsType(String csvHeader) {
        this.csvHeader = csvHeader;
    }

    public static Optional<StatsType> findByCsvHeader(String csvHeader) {
        for (StatsType statsType : StatsType.values()) {
            if (csvHeader.equals(statsType.csvHeader)) {
                return Optional.of(statsType);
            }
        }

        return Optional.empty();
    }
}
