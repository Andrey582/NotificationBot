package edu.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(
    value = "app.scheduler.enable",
    havingValue = "true"
)
public class LinkUpdateScheduler {

    public static final Logger LOGGER = LogManager.getLogger(LinkUpdateScheduler.class);

    @Scheduled(fixedDelayString = "#{ @scheduler.interval }")
    public void update() {
        LOGGER.info("Вызван метод update");
    }
}
