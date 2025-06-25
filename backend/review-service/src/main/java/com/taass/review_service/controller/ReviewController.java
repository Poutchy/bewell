package com.taass.review_service.controller;

import com.taass.review_service.model.Review;
import com.taass.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @GetMapping("/salon/{salonId}")
    public List<Review> getReviewsBySalonId(@PathVariable String salonId) {
        return reviewService.getReviewsBySalonId(salonId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUserId(@PathVariable String userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PutMapping("/{reviewId}/response")
    public Review addSalonResponse(
            @PathVariable String reviewId,
            @RequestBody String responseFromSalon) {

        return reviewService.addSalonResponse(reviewId, responseFromSalon);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
    }
}

