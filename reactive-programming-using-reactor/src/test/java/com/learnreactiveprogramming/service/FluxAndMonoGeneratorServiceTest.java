package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        // when
        Flux<String> stringFlux = fluxAndMonoGeneratorService.namesFlux();

        // then
        StepVerifier.create(stringFlux)
//                .expectNext("Alice", "Bob", "Carol")
//                .expectNextCount(3)
                .expectNext("Alice")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.namesFluxMap();
        StepVerifier.create(stringFlux)
                .expectNext("ALICE", "BOB", "CAROL")
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void namesMono() {
        Mono<String> stringMono = fluxAndMonoGeneratorService.namesMono();
        StepVerifier.create(stringMono)
                .expectNext("Shifat")
                .verifyComplete();
    }
}