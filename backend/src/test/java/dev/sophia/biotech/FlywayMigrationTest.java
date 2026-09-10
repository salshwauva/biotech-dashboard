package dev.sophia.biotech;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class FlywayMigrationTest extends AbstractPostgresTest {

    @Autowired
    DataSource dataSource;

    @Test
    void appliesEveryMigrationToAnEmptyDatabase() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        Integer failed = jdbc.queryForObject(
                "SELECT count(*) FROM flyway_schema_history WHERE success = false", Integer.class);
        Integer applied = jdbc.queryForObject(
                "SELECT count(*) FROM flyway_schema_history WHERE type <> 'SCHEMA'", Integer.class);

        assertThat(failed).isZero();
        assertThat(applied).isGreaterThanOrEqualTo(1);
    }

    @Test
    void recordsTheBaselineVersion() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        String description = jdbc.queryForObject(
                "SELECT description FROM flyway_schema_history WHERE version = '1'", String.class);

        assertThat(description).isEqualTo("baseline");
    }
}
