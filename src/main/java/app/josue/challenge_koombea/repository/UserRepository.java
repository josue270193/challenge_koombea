package app.josue.challenge_koombea.repository;

import app.josue.challenge_koombea.domain.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {

  Mono<UserEntity> findByUsername(String username);

}
