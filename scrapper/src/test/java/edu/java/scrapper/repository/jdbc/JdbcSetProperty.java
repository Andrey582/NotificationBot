package edu.java.scrapper.repository.jdbc;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public interface JdbcSetProperty {

    @DynamicPropertySource
    static void registerProperty(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("app.databaseAccessType", () -> "jpa");
    }
}
