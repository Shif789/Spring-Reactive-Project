package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MoviesInfoService {
    Mono<MovieInfo> insertMovieInfo(MovieInfo model);

    Flux<MovieInfo> fetchAllMovieInfo();

    Mono<MovieInfo> fetchMovieInfoById(String movieInfoId);

    Mono<MovieInfo> updateMovieInfo(MovieInfo model);

    Flux<MovieInfo> deleteMovieInfoById(String movieInfoId);
}
