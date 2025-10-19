package com.reactivespring.service.impl;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewRepository;
import com.reactivespring.service.ReviewService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Mono<Review> insertReview(Review model) {
        Mono<Review> savedReviewMono = reviewRepository.save(model);
        return savedReviewMono;
    }

    @Override
    public Flux<Review> getAllMovieReview() {
        return reviewRepository.findAll().log();
    }

    @Override
    public Mono<Review> fetchMovieReviewById(String reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Override
    public Flux<Review> fetchMovieReviewByMovieInfoId(Long movieInfoId) {
        return reviewRepository.findByMovieInfoId(movieInfoId).log();
    }
}
