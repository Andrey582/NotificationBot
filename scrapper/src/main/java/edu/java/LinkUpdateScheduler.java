package edu.java;

import edu.java.updater.JdbcLinkUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    JdbcLinkUpdater updater;

    @Scheduled(fixedDelayString = "#{ @scheduler.interval }")
    public void update() {
        LOGGER.info("update");
        updater.update();
    }
}
