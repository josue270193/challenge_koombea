package app.josue.challenge_koombea.handler;

import app.josue.challenge_koombea.domain.dto.ScrapperAddRequestDto;
import app.josue.challenge_koombea.domain.dto.ScrapperListRequestDto;
import app.josue.challenge_koombea.service.ScrapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ScrapperHandler {

  private final Logger log = LoggerFactory.getLogger(ScrapperHandler.class);

  @Autowired
  private ScrapperService scrapperService;

  public Mono<ServerResponse> search(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(ScrapperListRequestDto.class)
        .zipWith(serverRequest.principal())
        .flatMap(tuple2 -> {
          var request = tuple2.getT1();
          var username = tuple2.getT2().getName();
          var page = PageRequest.of(request.getPageNumber(), request.getPageSize());
          return scrapperService.search(username, page);
        })
        .flatMap(result ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(result))
        );
  }

  public Mono<ServerResponse> add(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(ScrapperAddRequestDto.class)
        .doOnNext(dto -> log.info("Request: {}", dto))
        .zipWith(serverRequest.principal())
        .flatMap(tuple2 -> {
          var request = tuple2.getT1();
          var username = tuple2.getT2().getName();
          return scrapperService.add(username, request.getUrl());
        })
        .flatMap(result ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(result))
        );
  }
}
