package com.reactivespring.service;

import com.reactivespring.domain.Review;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewService {
    Mono<Review> insertReview(Review model);

    Flux<Review> getAllMovieReview();

    Mono<Review> fetchMovieReviewById(String reviewId);

    Flux<Review> fetchMovieReviewByMovieInfoId(Long movieInfoId);
}
