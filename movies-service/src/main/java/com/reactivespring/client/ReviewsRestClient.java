package com.reactivespring.client;

import com.reactivespring.domain.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ReviewsRestClient {
    private WebClient webClient;

    @Value("${restClient.reviewsUrl}")
    private String moviesInfoUrl;

    public ReviewsRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Review> getMovieReviewByMovieId(Long movieInfoId) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8081)
                        .path("/v1/api/movies-review/fetch-review-by-movie-info-id")
                        .queryParam("movieInfoId", movieInfoId)
                        .build())
                .retrieve()
                .bodyToFlux(Review.class)
                .log();

    }
}
