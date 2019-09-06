package it.bz.davinci.innovationscoreboard.config.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SecurityConstants {
    // JWT token defaults
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "innovation-scoreboard";
    public static final String TOKEN_AUDIENCE = "innovation-scoreboard-api";
}
