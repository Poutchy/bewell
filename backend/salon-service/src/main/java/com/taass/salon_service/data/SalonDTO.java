package com.taass.salon_service.data;

import com.taass.salon_service.model.OpeningHour;
import com.taass.salon_service.model.Service;
import lombok.Data;

import java.util.List;

@Data
public class SalonDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String pricingRange;
    private List<OpeningHour> openingHours;
    private Double rating;
    private Integer totalReviews;
    private List<Service> services;
    private String website;
    private List<String> socialMediaLinks;
    private Double latitude;
    private Double longitude;
    private List<String> imageUrls;
}
