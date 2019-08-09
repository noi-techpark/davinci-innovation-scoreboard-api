package it.bz.davinci.innovationscoreboard.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;

        setFilterProcessesUrl("/api/v1/authenticate");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            Map<String, String> credentials = objectMapper
                    .readValue(request.getInputStream(), Map.class);

            String username = credentials.get(SPRING_SECURITY_FORM_USERNAME_KEY);
            String password = credentials.get(SPRING_SECURITY_FORM_PASSWORD_KEY);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] jwtSecretBytes = SecurityConstants.JWT_SECRET.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecretBytes), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact();

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }
}
