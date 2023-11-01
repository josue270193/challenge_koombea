package app.josue.challenge_koombea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ChallengeKoombeaApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChallengeKoombeaApplication.class, args);
  }

}
