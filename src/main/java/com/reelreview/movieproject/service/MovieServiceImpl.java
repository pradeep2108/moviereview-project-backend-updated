package com.reelreview.movieproject.service;

import com.reelreview.movieproject.model.Movie;
import com.reelreview.movieproject.repository.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{

    @Autowired
    MovieRepository movieRepository;
    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMoveByTitle(String title) {
        return movieRepository.findMovieByTitle(title);
    }

    @Override
    public Optional<Movie> getMoveByObjectId(ObjectId id) {
        return movieRepository.findById(id);
    }

    @Override
    public Movie findMovieByImdbId(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }

    @Override
    public Movie addNewMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
