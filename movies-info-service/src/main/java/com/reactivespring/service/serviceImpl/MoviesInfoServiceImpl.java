package com.reactivespring.service.serviceImpl;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.model.MovieInfoModel;
import com.reactivespring.repositories.MovieInfoRepository;
import com.reactivespring.service.MoviesInfoService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MoviesInfoServiceImpl implements MoviesInfoService {

    private final MovieInfoRepository movieInfoRepository;

    public MoviesInfoServiceImpl(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    @Override
    public Mono<MovieInfo> insertMovieInfo(MovieInfo model) {

//        MovieInfo movieInfo = new MovieInfo();
//        movieInfo.setMovieInfoId(model.getMovieInfoId());
//        movieInfo.setName(model.getName());
//        movieInfo.setYear(model.getYear());
//        movieInfo.setCast(model.getCast());
//        movieInfo.setReleaseDate(model.getReleaseDate());

        Mono<MovieInfo> savedMovieInfoMono = movieInfoRepository.save(model).log();
        return savedMovieInfoMono;
    }

    @Override
    public Flux<MovieInfo> fetchAllMovieInfo() {
        return movieInfoRepository.findAll();
    }

    @Override
    public Mono<MovieInfo> fetchMovieInfoById(String movieInfoId) {
        return movieInfoRepository.findById(movieInfoId).log();
    }

    @Override
    public Mono<MovieInfo> updateMovieInfo(MovieInfo model) {
        Mono<MovieInfo> existingMovieInfoMono = movieInfoRepository.findById(model.getMovieInfoId());
        return existingMovieInfoMono.flatMap(movieInfo -> {
            movieInfo.setMovieInfoId(model.getMovieInfoId());
            movieInfo.setYear(model.getYear());
            movieInfo.setName(model.getName());
            movieInfo.setCast(model.getCast());
            movieInfo.setReleaseDate(model.getReleaseDate());

            return movieInfoRepository.save(movieInfo);
        });
    }

    @Override
    public Flux<MovieInfo> deleteMovieInfoById(String movieInfoId) {
//        Mono<MovieInfo> movieInfoMono = movieInfoRepository.findById(movieInfoId).log();
        return movieInfoRepository.deleteById(movieInfoId).thenMany(movieInfoRepository.findAll());
    }
}
