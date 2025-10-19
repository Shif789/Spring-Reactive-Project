package com.reactivespring.controller;

import com.reactivespring.client.MoviesInfoRestClient;
import com.reactivespring.client.ReviewsRestClient;
import com.reactivespring.domain.Movie;
import com.reactivespring.domain.MovieInfo;
import com.reactivespring.domain.Review;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/api/movies")
public class MovieController {

    private ReviewsRestClient reviewsRestClient;
    private MoviesInfoRestClient moviesInfoRestClient;

    public MovieController(ReviewsRestClient reviewsRestClient, MoviesInfoRestClient moviesInfoRestClient) {
        this.reviewsRestClient = reviewsRestClient;
        this.moviesInfoRestClient = moviesInfoRestClient;
    }

//    @GetMapping("/fetch-by-movie-id/{id}")
//    public Mono<Movie> fetchMovieById(@PathVariable("id") Long movieId) {
//
//        return moviesInfoRestClient.getMovieInfoById(movieId.toString())
//                .flatMap(movieInfo -> {
//                    Mono<List<Review>> reviewListMono = reviewsRestClient.getMovieReviewByMovieId(movieId)
//                            .collectList();
//
//                    Mono<Movie> movieMono = reviewListMono.map(reviewList -> new Movie(movieInfo, reviewList));
//                    return movieMono;
//                });
//    }

    @GetMapping("/fetch-by-movie-id/{id}")
    public Mono<Movie> fetchMovieById(@PathVariable("id") Long movieId) {

        Mono<MovieInfo> movieInfoMono = moviesInfoRestClient.getMovieInfoById(movieId.toString());
        Mono<List<Review>> reviewListMono = reviewsRestClient.getMovieReviewByMovieId(movieId).collectList();

        return movieInfoMono
                .zipWith(reviewListMono, Movie::new)
                .log();
    }






}
