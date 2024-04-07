package edu.java;

import edu.java.updater.LinkUpdater;
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

    @Autowired
    private LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{ @scheduler.interval }")
    public void update() {
        LOGGER.info("update");
        linkUpdater.update();
    }

    public static final Logger LOGGER = LogManager.getLogger(LinkUpdateScheduler.class);
}
