package com.reelreview.movieproject.controller;
import com.reelreview.movieproject.model.Review;
import com.reelreview.movieproject.repository.ReviewRepository;
import com.reelreview.movieproject.service.ReviewService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reelreview")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/public/addreview")
    public ResponseEntity<?> createReview(@RequestBody Map<String, String> payload){
     return new ResponseEntity<Review>(reviewService.saveReview(payload.get("reviewBody"), payload.get("imdbId")), HttpStatus.CREATED);
    }

    @GetMapping("/public/getreview/{id}")
    public ResponseEntity<?> getReview(@PathVariable ObjectId id){
        return new ResponseEntity<String>(reviewService.getReviewByObjectId(id), HttpStatus.OK);
    }
}
