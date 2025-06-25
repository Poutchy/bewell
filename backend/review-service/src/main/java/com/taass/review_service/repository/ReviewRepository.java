package com.taass.review_service.repository;

import com.taass.review_service.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review,String> {
    List<Review> findBySalonId(String salonId);
    List<Review> findByUserId(String userId);
}
