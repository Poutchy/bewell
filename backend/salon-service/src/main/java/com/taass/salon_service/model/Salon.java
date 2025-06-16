package com.taass.salon_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

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

    private List<String> openingHours;

    private Double rating;
    private Integer totalReviews;

    @Relationship(type = "OFFERS", direction = OUTGOING)
    private List<Service> services;

    private String website;
    private List<String> socialMediaLinks;

    private Double latitude;
    private Double longitude;

    private List<String> imageUrls;

}
