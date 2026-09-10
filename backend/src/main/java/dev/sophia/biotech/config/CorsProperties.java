package dev.sophia.biotech.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Allowed browser origins. Development uses the local Vite origin. Production uses the deployed
 * frontend origin. A wildcard origin is not accepted.
 */
@ConfigurationProperties(prefix = "cors")
public record CorsProperties(List<String> allowedOrigins) {

    public CorsProperties {
        allowedOrigins = allowedOrigins == null ? List.of() : List.copyOf(allowedOrigins);
        if (allowedOrigins.contains("*")) {
            throw new IllegalArgumentException("cors.allowed-origins must not contain a wildcard");
        }
    }
}
