package app.josue.challenge_koombea.service.security;

import app.josue.challenge_koombea.domain.SecurityUser;
import app.josue.challenge_koombea.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserSecurityService implements ReactiveUserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(userModel -> new SecurityUser(userModel, List.of()));
  }
}
