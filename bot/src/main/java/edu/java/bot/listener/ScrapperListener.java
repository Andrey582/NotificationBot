package edu.java.bot.listener;

import edu.java.bot.dto.request.LinkUpdateRequestDto;
import edu.java.bot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ScrapperListener {

    @Autowired
    private BotService botService;

    @RetryableTopic(
        attempts = "1",
        kafkaTemplate = "kafkaProducer",
        dltTopicSuffix = "_dlq",
        include = RuntimeException.class
    )
    @KafkaListener(topics = "main", groupId = "listen", containerFactory = "kafkaListener")
    public void listen(@Payload LinkUpdateRequestDto linkUpdateRequestDto, Acknowledgment acknowledgment) {
        botService.update(linkUpdateRequestDto);
        acknowledgment.acknowledge();
    }
}
