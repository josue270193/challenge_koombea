package app.josue.challenge_koombea.service;

import app.josue.challenge_koombea.domain.dto.ScrapperAddResponseDto;
import app.josue.challenge_koombea.domain.dto.ScrapperLinkDto;
import app.josue.challenge_koombea.domain.dto.ScrapperListResponseDto;
import app.josue.challenge_koombea.domain.dto.ScrapperResponseDto;
import app.josue.challenge_koombea.domain.entity.ScrapperEntity;
import app.josue.challenge_koombea.domain.entity.ScrapperLinkEntity;
import app.josue.challenge_koombea.repository.ScrapperRepository;
import app.josue.challenge_koombea.service.producer.ScrapperProducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ScrapperService {

  private final Logger log = LoggerFactory.getLogger(ScrapperService.class);

  @Autowired
  private ScrapperRepository scrapperRepository;

  @Autowired
  private ScrapperProducer scrapperProducer;

  public Mono<ScrapperListResponseDto> search(String username, PageRequest page) {
    return Mono.zip(
            scrapperRepository.count(),
            scrapperRepository.findByUsername(username, page).collectList()
        )
        .map(tuple2 -> {
          var count = tuple2.getT1();
          var list = tuple2.getT2().stream()
              .map(scrapper -> new ScrapperResponseDto(
                  scrapper.getId(),
                  scrapper.getUrl(),
                  scrapper.getLinksFound()
                      .stream()
                      .map(entity -> new ScrapperLinkDto(entity.getHref(), entity.getText()))
                      .toList(),
                  scrapper.getIsDone(),
                  scrapper.getIsError() == null ? Boolean.FALSE : scrapper.getIsError(),
                  scrapper.getError()
              ))
              .toList();
          return new ScrapperListResponseDto(count, list);
        });
  }

  public Mono<ScrapperAddResponseDto> add(String username, String url) {
    var newScrapper = new ScrapperEntity(null, username, url, List.of(), false, false, null);
    return Mono.just(newScrapper)
        .flatMap(scrapperRepository::save)
        .doOnNext(entity -> scrapperProducer.sendMessage(entity.getUsername(), entity.getUrl()))
        .map(entity -> new ScrapperAddResponseDto(entity.getId(),
            entity.getUrl(),
            entity.getIsDone())
        );
  }

  public List<ScrapperLinkEntity> scrapperLinks(String url) throws IOException {
    var result = new ArrayList<ScrapperLinkEntity>();

    var doc = Jsoup.connect(url).get();
    for (var link : doc.select("a")) {
      result.add(
          new ScrapperLinkEntity(
              link.attr("href"),
              link.html()
          )
      );
    }

    return result;
  }
}
