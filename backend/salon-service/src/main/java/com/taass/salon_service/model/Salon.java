package com.taass.salon_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashMap;
import java.util.List;

@Node
@Data
@NoArgsConstructor
public class Salon {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String address;

    private String phone;
    private String email;

    private String pricingRange;

    @Relationship(type = "HAS_OPENING_HOURS")
    private List<OpeningHour> openingHours;

    private Double rating;
    private Integer totalReviews;

    @Relationship(type = "OFFERS")
    private List<Service> services;

    private String website;
    private List<String> socialMediaLinks;

    private Double latitude;
    private Double longitude;

    private List<String> imageUrls;

}
