package app.josue.challenge_koombea.repository;

import app.josue.challenge_koombea.domain.entity.ScrapperEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ScrapperRepository extends ReactiveMongoRepository<ScrapperEntity, String> {

  Flux<ScrapperEntity> findByUsername(String username, Pageable pageable);

  Flux<ScrapperEntity> findByUsernameAndUrlAndIsDoneFalse(String username, String url);

  Mono<ScrapperEntity> findByUrlAndIsDone(String url, Boolean isDone);

}
