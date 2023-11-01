package app.josue.challenge_koombea.handler;

import app.josue.challenge_koombea.domain.dto.UserCreationRequestDto;
import app.josue.challenge_koombea.domain.dto.UserCreationResponseDto;
import app.josue.challenge_koombea.domain.dto.UserInfoResponseDto;
import app.josue.challenge_koombea.domain.dto.UserLoginRequestDto;
import app.josue.challenge_koombea.domain.dto.UserLoginResponseDto;
import app.josue.challenge_koombea.domain.entity.UserEntity;
import app.josue.challenge_koombea.security.JwtTokenProvider;
import app.josue.challenge_koombea.service.UserService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

  @Autowired
  private ReactiveAuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider tokenProvider;

  @Autowired
  private UserService userService;

  public Mono<ServerResponse> register(ServerRequest request) {
    return request.bodyToMono(UserCreationRequestDto.class)
//        .doOnNext(this::validate)
        .map(dto -> new UserEntity(null, dto.getUsername(), dto.getName(), dto.getLastname(),
            dto.getPassword(), true)
        )
        .flatMap(userService::register)
        .map(user -> new UserCreationResponseDto(user.getId()))
        .flatMap(userDto ->
            ServerResponse.created(URI.create("/login"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
        );
  }

//  private void validate(UserCreationRequestDto dto) {
//    var errors = new BeanPropertyBindingResult(dto, "person");
//    validator.validate(dto, errors);
//    if (errors.hasErrors()) {
//      throw new ServerWebInputException(errors.toString());
//    }
//  }

  public Mono<ServerResponse> getInfo(ServerRequest request) {
    return request.principal()
        .flatMap(principal -> userService.getInfoByName(principal.getName()))
        .map(userEntity -> new UserInfoResponseDto(userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getName(),
            userEntity.getLastname(),
            userEntity.isEnabled())
        )
        .flatMap(user -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(user))
        );
  }

  public Mono<ServerResponse> login(ServerRequest request) {
    return request.bodyToMono(UserLoginRequestDto.class)
        .flatMap(userModel -> login(userModel.getUsername(), userModel.getPassword()));
  }

  public Mono<ServerResponse> login(String username, String password) {
    return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                username, password
            )
        )
        .map(tokenProvider::createToken)
        .map(UserLoginResponseDto::new)
        .flatMap(userDto ->
            ServerResponse.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userDto.getJwt())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
        );
  }

}
