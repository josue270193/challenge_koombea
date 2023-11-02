package app.josue.challenge_koombea.service;

import app.josue.challenge_koombea.domain.entity.ScrapperEntity;
import app.josue.challenge_koombea.repository.ScrapperRepository;
import app.josue.challenge_koombea.service.producer.ScrapperProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ScrapperServiceTest {

    @Mock
    private ScrapperRepository scrapperRepository;

    @Mock
    private ScrapperProducer scrapperProducer;

    @InjectMocks
    private ScrapperService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenSearchOkThenOk() {
        var username = "test";
        var page = PageRequest.of(0, 10);

        when(scrapperRepository.count())
                .thenReturn(Mono.just(0L));
        when(scrapperRepository.findByUsername(eq(username), any()))
                .thenReturn(Flux.just());

        var result = service.search(username, page);
        StepVerifier.create(result)
                .expectNextMatches(item -> item.getCount() == 0 && item.getContent().isEmpty())
                .verifyComplete();
        verify(scrapperRepository, times(1)).count();
        verify(scrapperRepository, times(1)).findByUsername(any(), any());
        verify(scrapperProducer, times(0)).sendMessage(any(), any());
    }


    @Test
    void whenAddOkThenOk() {
        var username = "test";
        var url = "url";

        when(scrapperRepository.save(any()))
                .thenReturn(Mono.just(new ScrapperEntity("", username, url, List.of(), Boolean.FALSE, Boolean.FALSE, null)));
        doNothing().when(scrapperProducer).sendMessage(eq(username), eq(url));

        var result = service.add(username, url);
        StepVerifier.create(result)
                .expectNextMatches(item -> item.getId() != null)
                .verifyComplete();
        verify(scrapperRepository, times(0)).count();
        verify(scrapperRepository, times(0)).findByUsername(any(), any());
        verify(scrapperRepository, times(1)).save(any());
        verify(scrapperProducer, times(1)).sendMessage(any(), any());
    }
}
