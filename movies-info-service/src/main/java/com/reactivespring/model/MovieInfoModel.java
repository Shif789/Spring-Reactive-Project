package com.reactivespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfoModel {
    private String movieInfoId;
    private String name;
    private Integer year;
    private List<String> cast;
    private LocalDate releaseDate;
}
