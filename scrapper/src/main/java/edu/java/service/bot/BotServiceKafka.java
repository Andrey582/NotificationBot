package edu.java.service.bot;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import edu.java.service.BotService;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Mono;

public class BotServiceKafka implements BotService {

    private ApplicationConfig applicationConfig;
    private KafkaTemplate<String, BotLinkUpdateRequestDto> kafkaTemplate;

    public BotServiceKafka(
        ApplicationConfig applicationConfig,
        KafkaTemplate<String, BotLinkUpdateRequestDto> kafkaTemplate
    ) {
        this.applicationConfig = applicationConfig;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Void> sendLinkUpdate(BotLinkUpdateRequestDto botLinkUpdateRequestDto) {
        kafkaTemplate.send(applicationConfig.kafkaProp().topicProp().name(), botLinkUpdateRequestDto);
        return Mono.empty();
    }
}
