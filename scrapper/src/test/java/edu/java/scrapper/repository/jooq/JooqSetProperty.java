package edu.java.scrapper.repository.jooq;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public interface JooqSetProperty {

    @DynamicPropertySource
    static void registerProperty(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("app.databaseAccessType", () -> "jpa");
    }
}
