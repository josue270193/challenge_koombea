package app.josue.challenge_koombea.handler;

import app.josue.challenge_koombea.ChallengeKoombeaApplication;
import app.josue.challenge_koombea.config.RouteConfig;
import app.josue.challenge_koombea.domain.dto.ScrapperListRequestDto;
import app.josue.challenge_koombea.domain.dto.ScrapperListResponseDto;
import app.josue.challenge_koombea.domain.dto.ScrapperResponseDto;
import app.josue.challenge_koombea.service.ScrapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = ChallengeKoombeaApplication.class)
public class ScrapperHandlerTest {

    @Autowired
    private RouteConfig routeConfig;

    @Autowired
    private ScrapperHandler handler;

    @MockBean
    private ScrapperService scrapperService;

    @BeforeEach
    void setUp() {
        handler.setScrapperService(scrapperService);
    }

    @Test
    void whenSearchOkThenOk() {

        var list = List.of(
                new ScrapperResponseDto(
                        UUID.randomUUID().toString(),
                        "url",
                        List.of(),
                        Boolean.FALSE,
                        Boolean.FALSE,
                        null
                )
        );
        when(scrapperService.search(anyString(), any()))
                .thenReturn(Mono.just(
                        new ScrapperListResponseDto((long) list.size(), list)
                ));

        var client = WebTestClient
                .bindToRouterFunction(routeConfig.scrapperRoutes(handler))
                .build();

        var request = new ScrapperListRequestDto();
        client.post()
                .uri("/scrapper/search")
                .body(Mono.just(request), ScrapperListRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

}
