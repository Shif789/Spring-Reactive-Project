package com.learnreactiveprogramming.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {

    private final Logger LOGGER = LoggerFactory.getLogger(FluxAndMonoGeneratorService.class);

    public Flux<String> namesFlux() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("Alice", "Bob", "Carol")).log();// in reality flux(publisher) will be from db/external api calls
        return stringFlux;
//        return Flux.just("A", "B", "C");
    }

    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("Alice", "Bob", "Carol"))
                .map(s -> {
                    s = s.toUpperCase();
                    return s;
                })// applying transformation
                .log();

//        Flux<String> stringFlux = Flux.fromIterable(List.of("Alice", "Bob", "Carol"));
//         stringFlux.map(String::toUpperCase);
//         return stringFlux; // the above and this return is not going to be same. String flux is immutable so to return the Upper case values we need to return the above statement
    }

    public Mono<String> namesMono() {
        Mono<String> stringMono = Mono.just("Shifat").log();
        return stringMono;
    }

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        System.out.println("for flux");
        fluxAndMonoGeneratorService.namesFlux().subscribe(s -> {
            System.out.println("Name is : " + s);
        });

        System.out.println("for mono");
        fluxAndMonoGeneratorService.namesMono().subscribe(s -> {
            System.out.println("Name is : " + s);
        });
    }
}

