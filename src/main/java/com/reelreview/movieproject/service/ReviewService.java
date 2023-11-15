package com.reelreview.movieproject.service;

import com.reelreview.movieproject.model.Review;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface ReviewService {

    Review saveReview(String reviewBody, String imdbId);

    String getReviewByObjectId(ObjectId id);
}
