package com.reelreview.movieproject.controller;

import com.reelreview.movieproject.model.Movie;
import com.reelreview.movieproject.service.MovieService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reelreview")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/public/allmovies")
    public ResponseEntity<?> getAllMovies(){

        try{
          List<Movie> allMovies = movieService.getAllMovies();

          if (!allMovies.isEmpty()){
              return new ResponseEntity<List<Movie>>(allMovies, HttpStatus.OK);
          }else {

              return new ResponseEntity<String>("Currently no movies found", HttpStatus.NO_CONTENT);

          }
        }catch (Exception e){

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/public/movie/title/{title}")
    public ResponseEntity<?> getMovieByTitle(@PathVariable String title){
        try{
            Movie findMovie = movieService.getMoveByTitle(title);
            if (findMovie != null){
                return new ResponseEntity<Movie>(findMovie, HttpStatus.OK);
            }else {

                return new ResponseEntity<String>("Movie not found", HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/admin/movie/id/{id}")
    public ResponseEntity<?> getMovieByObjectId(@PathVariable ObjectId id){
        try{
//            Optional<Movie> findMovie = movieService.getMoveByObjectId(id);
//            if (findMovie != null){
//            System.out.println(findMovie);
                return new ResponseEntity<Optional<Movie>>(movieService.getMoveByObjectId(id), HttpStatus.OK);
//            }else {

//                return new ResponseEntity<String>("Movie not found", HttpStatus.NO_CONTENT);
//            }
        }catch (Exception e){

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/public/movie/imdbid/{imdbId}")
    public ResponseEntity<?> getMovieByImdbId(@PathVariable String imdbId){
        try{
//            Optional<Movie> findMovie = movieService.getMoveByObjectId(id);
//            if (findMovie != null){
//            System.out.println(findMovie);
            return new ResponseEntity<Movie>(movieService.findMovieByImdbId(imdbId), HttpStatus.OK);
//            }else {

//                return new ResponseEntity<String>("Movie not found", HttpStatus.NO_CONTENT);
//            }
        }catch (Exception e){

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/admin/addmovie")
    public ResponseEntity<?> addNewMovie(@RequestBody Movie movie){

        try{
            movieService.addNewMovie(movie);
            return new ResponseEntity<String>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
