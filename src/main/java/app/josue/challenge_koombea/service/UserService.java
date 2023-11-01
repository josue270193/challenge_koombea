package app.josue.challenge_koombea.service;

import app.josue.challenge_koombea.domain.entity.UserEntity;
import app.josue.challenge_koombea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  public Mono<UserEntity> register(UserEntity userEntityToCreated) {
    var encryptedPassword = passwordEncoder.encode(userEntityToCreated.getPassword());
    var savedUser = userEntityToCreated.copyForEncryption(encryptedPassword);
    return userRepository.save(savedUser);
  }

  public Mono<UserEntity> getInfoByName(String username) {
    return userRepository.findByUsername(username);
  }

}
