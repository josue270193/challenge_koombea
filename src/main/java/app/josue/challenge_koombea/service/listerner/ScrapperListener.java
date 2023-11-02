package app.josue.challenge_koombea.service.listerner;

import app.josue.challenge_koombea.config.KafkaConfiguration;
import app.josue.challenge_koombea.domain.entity.ScrapperLinkEntity;
import app.josue.challenge_koombea.domain.topic.ScrapperTopic;
import app.josue.challenge_koombea.repository.ScrapperRepository;
import app.josue.challenge_koombea.service.ScrapperService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ScrapperListener {

  private final Logger log = LoggerFactory.getLogger(ScrapperListener.class);

  @Autowired
  private ScrapperRepository scrapperRepository;

  @Autowired
  private ScrapperService scrapperService;

  @KafkaListener(topics = KafkaConfiguration.SCRAPPER_TOPIC)
  public void listener(ScrapperTopic data) {
    var list = new ArrayList<ScrapperLinkEntity>();
    String error = null;

    try {
      list.addAll(scrapperService.scrapperLinks(data.getUrl()));
    } catch (Exception e) {
      error = e.getMessage();
      log.error("Error on scrapping", e);
    }

    String finalError = error;
    Mono.just(data)
        .doOnNext(topic -> log.info("Topic: {}", topic))
        .flatMapMany(topic -> scrapperRepository.findByUsernameAndUrlAndIsDoneFalse(topic.getUsername(), topic.getUrl()))
        .map(entity -> {
          entity.setIsDone(true);
          entity.setLinksFound(list);
          entity.setIsError(finalError != null);
          entity.setError(finalError);
          return entity;
        })
        .flatMap(scrapperRepository::save)
        .subscribe();
  }

}
