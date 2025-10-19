package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MoviesInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api/movies-info")
public class MoviesInfoController {

    private final MoviesInfoService service;

    public MoviesInfoController(MoviesInfoService service) {
        this.service = service;
    }

    @PostMapping("/create-movie-info")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> createMovieInfo(@RequestBody @Valid MovieInfo model) {
        return service.insertMovieInfo(model).log();
    }

    @GetMapping("/get-all-movie-info")
//    @ResponseStatus(HttpStatus.OK)
    public Flux<MovieInfo> getAllMovieInfo() {
        return service.fetchAllMovieInfo().log();
    }

    @GetMapping("/get-movie-info-by-id")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovieInfo> getMovieInfoById(@RequestParam String movieInfoId) {
        return service.fetchMovieInfoById(movieInfoId).log();
    }

    @PutMapping("/update-movie-info")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovieInfo> updateMovieInfo(@RequestBody @Valid MovieInfo model) {
        return service.updateMovieInfo(model).log();
    }

    @DeleteMapping("/delete-movie-info-by-id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Flux<MovieInfo> deleteMovieInfoById(@RequestParam String movieInfoId) {
        return service.deleteMovieInfoById(movieInfoId).log();
    }


}
