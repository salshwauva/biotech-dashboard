package dev.sophia.biotech;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Base for tests that need the real schema. The container starts empty and Flyway builds the schema
 * from the migrations under {@code db/migration}, the same way a fresh deployment does.
 *
 * <p>The container is started once per JVM and shared by every subclass. Ryuk stops it when the
 * test run ends.
 */
public abstract class AbstractPostgresTest {

    @ServiceConnection
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    static {
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void requireFlyway(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.enabled", () -> true);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }
}
