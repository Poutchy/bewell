package com.taass.salon_service.repository;

import com.taass.salon_service.model.Salon;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface SalonRepository extends Neo4jRepository<Salon, Long> {
    @Query("MATCH (s:Salon) WHERE id(s) = $id RETURN s { .id, .name, .description, .address, .phone, .email, .pricingRange, .openingHours, .rating, .totalReviews, .website, .socialMediaLinks, .latitude, .longitude, .imageUrls } AS salon")
    Salon getSalonById(Long id);

}
