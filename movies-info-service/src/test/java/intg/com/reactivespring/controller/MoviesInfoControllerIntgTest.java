package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repositories.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MoviesInfoControllerIntgTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    private String baseUrlMovieInfo = "/v1/api/movies-info";

    @BeforeEach
    void setUp() {

        List<MovieInfo> movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movieInfos)
                .blockLast();// needs to make sure this completes before running the test cases// can block it in test classes only else exception
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }


    @Test
    void createMovieInfo() {
        MovieInfo movieInfo = new MovieInfo(null, "Batman Never Existed",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        webTestClient
                .post()
                .uri(baseUrlMovieInfo+ "/create-movie-info")
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieInfo savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(savedMovieInfo);
                    assertNotNull(savedMovieInfo.getMovieInfoId());
                    assertEquals(movieInfo.getName(), savedMovieInfo.getName());
                });
    }

    @Test
    void getAllMovieInfo() {
        webTestClient
                .get()
                .uri(baseUrlMovieInfo+ "/get-all-movie-info")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

    @Test
    void getMovieInfoById() {
        String id = "abc";
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrlMovieInfo+ "/get-movie-info-by-id")
                        .queryParam("movieInfoId", id)
                        .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Dark Knight Rises");
//                .expectBody(MovieInfo.class)
//                .consumeWith(movieInfoEntityExchangeResult -> {
//                    MovieInfo savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
//                    assertNotNull(savedMovieInfo);
//                    assertEquals(id, savedMovieInfo.getMovieInfoId());
//                })
        ;
        // both works fine
    }

    @Test
    void updateMovieInfo() {
        MovieInfo movieInfo = new MovieInfo("abc", "Dark Knight Rises",
                2022, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20"));

        webTestClient
                .put()
                .uri(baseUrlMovieInfo+ "/update-movie-info")
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieInfo savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(savedMovieInfo);
                    assertEquals(movieInfo.getYear(), savedMovieInfo.getYear());
                    assertEquals(movieInfo.getName(), savedMovieInfo.getName());
                });
    }

    @Test
    void deleteMovieInfoById() {
        String id = "abc";
        webTestClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrlMovieInfo+ "/delete-movie-info-by-id")
                        .queryParam("movieInfoId", id)
                        .build()
                )
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBodyList(MovieInfo.class)
                .hasSize(2);
    }
}