package com.reactivespring.controller;

import com.reactivespring.domain.Review;
import com.reactivespring.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/api/movies-review")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService reviewService) {
        this.service = reviewService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Review> createMovieReview(@RequestBody Review model) {
        return service.insertReview(model).log();
    }

    @GetMapping("/fetch-all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Review> fetchAllMovieReview() {
        return service.getAllMovieReview().log();
    }

    @GetMapping("/fetch-review-by-id")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Review> getMovieInfoById(@RequestParam String reviewId) {
        return service.fetchMovieReviewById(reviewId).log();
    }

    @GetMapping("/fetch-review-by-movie-info-id")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Review> getMovieReviewByMovieInfoId(@RequestParam Long movieInfoId) {
        return service.fetchMovieReviewByMovieInfoId(movieInfoId).log();
    }

}
