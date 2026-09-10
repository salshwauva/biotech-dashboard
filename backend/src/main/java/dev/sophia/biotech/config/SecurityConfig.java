package dev.sophia.biotech.config;

import java.util.List;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Route allowlist for a public, read-only API. There are no accounts and no login. Spring Security
 * is used to keep unintended routes closed, not to authenticate anyone.
 */
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class SecurityConfig {

    /** Public analytics routes. Extended deliberately as PR 5 adds endpoints. */
    private static final String API_PATTERN = "/api/**";

    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        return http
                // Picks up the corsConfigurationSource bean below by name.
                .cors(Customizer.withDefaults())
                // CSRF protection guards a browser session that carries credentials automatically.
                // This API has no session, no cookie, and no state-changing endpoint, so there is
                // nothing for a forged request to ride on. Revisit if any of those three change.
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requestCache(cache -> cache.disable())
                .anonymous(anonymous -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, API_PATTERN).permitAll()
                        .requestMatchers(HttpMethod.GET, API_PATTERN).permitAll()
                        .requestMatchers(HttpMethod.HEAD, API_PATTERN).permitAll()
                        .requestMatchers(EndpointRequest.to("health")).permitAll()
                        .anyRequest().denyAll())
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(CorsProperties properties) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(properties.allowedOrigins());
        config.setAllowedMethods(List.of("GET", "HEAD", "OPTIONS"));
        config.setAllowedHeaders(List.of("Accept", "Content-Type"));
        config.setAllowCredentials(false);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(API_PATTERN, config);
        return source;
    }
}
