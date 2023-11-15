package com.reelreview.movieproject.service;

import com.reelreview.movieproject.model.Movie;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMoveByTitle(String title);
    Optional<Movie> getMoveByObjectId(ObjectId id);
    Movie findMovieByImdbId(String imdbId);


    Movie addNewMovie(Movie movie);
}
