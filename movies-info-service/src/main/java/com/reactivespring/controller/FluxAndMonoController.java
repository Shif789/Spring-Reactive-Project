package com.reactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
//@RequestMapping("/v1/api/flux-mono-controller")
public class FluxAndMonoController {

    @GetMapping("/fetch-flux")
    public Flux<Integer> getFlux() {
        return  Flux.just(1, 2, 3, 4, 5).log();
    }

    @GetMapping("/fetch-mono")
    public Mono<Integer> getMono() {
        return  Mono.just(1).log();
    }

    @GetMapping(value = "/fetch-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> getStream() {
        return  Flux.interval(Duration.ofSeconds(1)).log();
    }

}
