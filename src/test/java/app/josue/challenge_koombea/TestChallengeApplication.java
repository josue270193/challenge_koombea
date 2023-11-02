package app.josue.challenge_koombea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestChallengeApplication {

  @Bean
  @ServiceConnection
  KafkaContainer kafkaContainer() {
    return new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.5"));
  }

  @Bean
  @ServiceConnection
  MongoDBContainer mongoDbContainer() {
    return new MongoDBContainer(DockerImageName.parse("mongo:7.0.2"));
  }

  public static void main(String[] args) {
    SpringApplication.from(ChallengeKoombeaApplication::main)
        .with(TestChallengeApplication.class)
        .run(args);
  }

}