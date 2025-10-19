package com.reactivespring.controller;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class ReviewControllerIntgTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ReviewRepository reviewRepository;

    private String baseUrlMovieInfo = "/v1/api/movies-review";

    @BeforeEach
    void setUp() {
        Review review1 = new Review(null, 1L, "very good movie", 8.50);
        Review review2 = new Review("abc", 1L, "average movie", 5.50);
        Review review3 = new Review("bbb", 2L, "great movie", 5.50);

        List<Review> reviewList = List.of(review1, review2, review3);

        reviewRepository.saveAll(reviewList).blockLast();
    }

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll().block();
    }


    @Test
    void createMovieReview() {
        Review review = new Review(null, 1L, "fantastic good movie", 9.50);

        webTestClient
                .post()
                .uri(baseUrlMovieInfo+ "/create")
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Review.class)
                .consumeWith(movieReviewEntityExchangeResult -> {
                    Review reviewResponse = movieReviewEntityExchangeResult.getResponseBody();
                    assertNotNull(reviewResponse);
                    assertNotNull(reviewResponse.getReviewId());
                    assertEquals(reviewResponse.getComment(), review.getComment());
                });
    }

    @Test
    void fetchAllMovieReview() {
        webTestClient
                .get()
                .uri(baseUrlMovieInfo+ "/fetch-all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Review.class)
                .hasSize(3);
    }

    @Test
    void getMovieInfoById() {
        String id = "abc";
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrlMovieInfo+ "/fetch-review-by-id")
                        .queryParam("reviewId", id)
                        .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.comment").isEqualTo("average movie");
//                .expectBody(MovieInfo.class)
//                .consumeWith(movieInfoEntityExchangeResult -> {
//                    MovieInfo savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
//                    assertNotNull(savedMovieInfo);
//                    assertEquals(id, savedMovieInfo.getMovieInfoId());
//                })
    }

    @Test
    void getMovieReviewByMovieInfoId() {
        Long movieInfoId = 1L;
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrlMovieInfo+ "/fetch-review-by-movie-info-id")
                        .queryParam("movieInfoId", movieInfoId)
                        .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Review.class)
                .hasSize(2);

    }
}