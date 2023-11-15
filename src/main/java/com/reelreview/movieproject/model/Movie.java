package com.reelreview.movieproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {

   @Id
   private ObjectId id;
   private String imdbId;
   private String title;
   private String releaseDate;
   private String trailerLink;
   private List<String> genres;
   private String poster;
   private List<String> backdrops;
   private String description;
   private String duration;
   private String director;
   private List<String> writers;
   private List<String> stars;
   @DocumentReference
   private List<Review> reviewIds;


}


