package com.taass.salon_service.repository;

import com.taass.salon_service.model.Salon;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface SalonRepository extends Neo4jRepository<Salon, Long> {
    @Query("MATCH (sal:Salon), (srv:Service) " +
            "WHERE id(sal) = $salonId AND id(srv) = $serviceId " +
            "CREATE (sal)-[:OFFERS]->(srv)")
    void createOffersRelationship(Long salonId, Long serviceId);

    @Query("MATCH (s:Salon), (t:TimeSlot) " +
            "WHERE id(s) = $salonId AND id(t) = $timeSlotId " +
            "MERGE (s)-[:HAS_TIME_SLOT]->(t) " +
            "RETURN s, t")
    void createHasTimeSlotRelationship(Long salonId, Long timeSlotId);
}
