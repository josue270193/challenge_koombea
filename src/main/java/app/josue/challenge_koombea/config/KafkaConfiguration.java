package app.josue.challenge_koombea.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

  public static final String SCRAPPER_TOPIC = "scrapper_topic";
  private final int PARTITIONS = 5;
  private final int REPLICAS = 3;

  @Bean
  public NewTopic scrapperTopic() {
    return TopicBuilder.name(SCRAPPER_TOPIC)
        .partitions(PARTITIONS)
        .replicas(REPLICAS)
        .build();
  }

}
