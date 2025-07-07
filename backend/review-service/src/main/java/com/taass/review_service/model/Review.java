package com.taass.review_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
public class Review {
    @Id
    private String reviewId;

    private String userId;
    private String salonId;
    private int rating;
    private String comment;
    private LocalDateTime timestamp;

    private String responseFromSalon;
}
