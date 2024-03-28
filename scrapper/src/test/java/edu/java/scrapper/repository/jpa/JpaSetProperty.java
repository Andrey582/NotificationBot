package edu.java.scrapper.repository.jpa;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public interface JpaSetProperty {

    @DynamicPropertySource
    static void registerProperty(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("app.databaseAccessType", () -> "jpa");
    }
}
