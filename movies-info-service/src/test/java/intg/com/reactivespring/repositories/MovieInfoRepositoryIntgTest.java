package com.reactivespring.repositories;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntgTest {

    @Autowired
    private MovieInfoRepository movieInfoRepository;

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
    void findAll() {
        Flux<MovieInfo> movieInfos = movieInfoRepository.findAll().log();
        StepVerifier
                .create(movieInfos)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        Mono<MovieInfo> movieInfos = movieInfoRepository.findById("abc").log();
        StepVerifier
                .create(movieInfos)
//                .expectNextCount(1)
                .assertNext(movieInfo -> {
                    assertEquals("Dark Knight Rises", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {
        MovieInfo movieInfo = new MovieInfo(null, "Dark Knight Dawn",
                2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18"));

        Mono<MovieInfo> savedMovieInfo = movieInfoRepository.save(movieInfo).log();

        StepVerifier
                .create(savedMovieInfo)
//                .expectNextCount(1)
                .assertNext(movieInfoTest -> {
                    assertNotNull(movieInfoTest.getMovieInfoId());
                    assertEquals("Dark Knight Dawn", movieInfoTest.getName());
                })
                .verifyComplete();
    }

    @Test
    void updateMovieInfo() {

        Mono<MovieInfo> movieInfoMono = movieInfoRepository.findById("abc").log();
        MovieInfo movieInfo = movieInfoMono.block();
        movieInfo.setYear(2021);
        Mono<MovieInfo> updatedMovieInfo = movieInfoRepository.save(movieInfo).log();

        StepVerifier
                .create(updatedMovieInfo)
//                .expectNextCount(1)
                .assertNext(movieInfoTest -> {
                    assertNotNull(movieInfoTest.getMovieInfoId());
                    assertEquals(2021, movieInfoTest.getYear());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo() {

        Mono<MovieInfo> movieInfoMono = movieInfoRepository.findById("abc").log();
        MovieInfo movieInfo = movieInfoMono.block();

        movieInfoRepository.delete(movieInfo).block();

        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier
                .create(movieInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}