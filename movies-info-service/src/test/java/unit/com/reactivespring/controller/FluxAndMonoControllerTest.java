package com.reactivespring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

@WebFluxTest(controllers = FluxAndMonoController.class)
@AutoConfigureWebTestClient
class FluxAndMonoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getFlux() {
        webTestClient
                .get()
                .uri("/fetch-flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(5);
    }

    @Test
    void getFluxApproach2() {
        Flux<Integer> resultFlux = webTestClient
                .get()
                .uri("/fetch-flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier
                .create(resultFlux)
                .expectNext(1,2,3,4,5)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getFluxApproach3() {
        webTestClient
                .get()
                .uri("/fetch-flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(exchangeResponseBody -> {
                    List<Integer> responseBody = exchangeResponseBody.getResponseBody();
                    assert (Objects.requireNonNull(responseBody).size() == 5);
                });
    }

    @Test
    void getMono() {
        webTestClient
                .get()
                .uri("/fetch-mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)// this works too
                .hasSize(1);
    }

    @Test
    void getMonoApproach3() {
        webTestClient
                .get()
                .uri("/fetch-mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Integer.class)
                .consumeWith(exchangeResponseBody -> {
                    Integer responseBody = exchangeResponseBody.getResponseBody();
                    assert (Objects.requireNonNull(responseBody).equals(1));
                });
    }

    @Test
    void getStream() {
        Flux<Long> resultFlux = webTestClient
                .get()
                .uri("/fetch-stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier
                .create(resultFlux)
                .expectNext(0L,1L,2L,3L,4L,5L)
//                .expectNextCount(6)// checks next 6 elements after 5L
                .thenCancel()
                .verify();
    }
}