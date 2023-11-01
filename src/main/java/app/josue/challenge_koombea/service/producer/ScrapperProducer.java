package app.josue.challenge_koombea.service.producer;

import app.josue.challenge_koombea.config.KafkaConfiguration;
import app.josue.challenge_koombea.domain.topic.ScrapperTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ScrapperProducer {

  @Autowired
  private KafkaTemplate<String, ScrapperTopic> kafkaTemplate;

  public void sendMessage(String username, String url) {
    ScrapperTopic topic = null;
    if (StringUtils.hasText(username) && StringUtils.hasText(url)) {
      topic = new ScrapperTopic(username, url);
    }

    if (topic != null) {
      Message<ScrapperTopic> message = MessageBuilder
          .withPayload(topic)
          .setHeader(KafkaHeaders.TOPIC, KafkaConfiguration.SCRAPPER_TOPIC)
          .build();
      kafkaTemplate.send(message);
    }
  }

}
