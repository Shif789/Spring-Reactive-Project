package com.reactivespring.client;

import com.reactivespring.domain.MovieInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MoviesInfoRestClient {
    private WebClient webClient;

    @Value("${restClient.moviesInfoUrl}")
    private String moviesInfoUrl;

    public MoviesInfoRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

//    public Mono<MovieInfo> getMovieInfoById(String movieInfoId) {
//        return webClient
//                .get()
//                .uri(
//                        uriBuilder -> uriBuilder
////                                .path(moviesInfoUrl + "/get-movie-info-by-id")
//                                .path("http://localhost:8080/v1/api/movies-info/get-movie-info-by-id")
//                                .queryParam("movieInfoId", movieInfoId)
//                                .build())
//                .retrieve()
//                .bodyToMono(MovieInfo.class)
//                .log();
//
//    }
public Mono<MovieInfo> getMovieInfoById(String movieInfoId) {
    return webClient
            .get()
            .uri(uriBuilder -> uriBuilder
                    .scheme("http")
                    .host("localhost")
                    .port(8080)
                    .path("/v1/api/movies-info/get-movie-info-by-id")
                    .queryParam("movieInfoId", movieInfoId)
                    .build())
            .retrieve()
            .bodyToMono(MovieInfo.class)
            .log();
}


}
