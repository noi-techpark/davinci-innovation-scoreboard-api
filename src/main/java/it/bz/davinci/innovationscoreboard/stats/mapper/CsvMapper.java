package it.bz.davinci.innovationscoreboard.stats.mapper;

public interface CsvMapper<CSV, ES> {
    public ES toEs(CSV csv);
}
