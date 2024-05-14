package ru.rsreu.ChineseCourse;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;


@Testcontainers
public abstract class IntegrationEnvironment {
    protected static final JdbcDatabaseContainer<?> CONTAINER;
    private static String DB_NAME = "chinese_course";
    private static String USER_NAME = "arina";
    private static String PASSWORD = "0000";

    static {
        DockerImageName imgName = DockerImageName.parse("postgres:latest");
        CONTAINER = new PostgreSQLContainer<>(imgName)
                .withDatabaseName(DB_NAME)
                .withUsername(USER_NAME)
                .withPassword(PASSWORD)
                .withExposedPorts(PostgreSQLContainer.POSTGRESQL_PORT);

//        CONTAINER.setWaitStrategy(Wait.defaultWaitStrategy()
//                .withStartupTimeout(Duration.of(60, SECONDS)));
        CONTAINER.start();

        runMigrations(CONTAINER);

    }

    @Configuration
    public static class JpaConfig{
        @Bean
        public DataSource testDataSource() {
            return DataSourceBuilder.create()
                    .url(CONTAINER.getJdbcUrl())
                    .username(CONTAINER.getUsername())
                    .password(CONTAINER.getPassword())
                    .build();
        }
    }

    public static void runMigrations(JdbcDatabaseContainer<?> c){
        var cangelogpath = new File(".").toPath().toAbsolutePath().getParent()
                .resolve("src")
                .resolve("main")
                .resolve("resources").resolve("db").resolve("changelog");

        try {
            var conn = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword());
            var changelogDir = new DirectoryResourceAccessor(cangelogpath);

            var db = new PostgresDatabase();
            db.setConnection(new JdbcConnection(conn));

            var liquibase = new Liquibase("master.xml", changelogDir, db);
            liquibase.update(new Contexts(), new LabelExpression());

        } catch (FileNotFoundException | SQLException | LiquibaseException e){
            throw new RuntimeException();
        }

    }

}