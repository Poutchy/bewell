package com.taass.salon_service.repository;

import com.taass.salon_service.model.Service;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface ServiceRepository extends Neo4jRepository<Service, Long> {
    @Query("MATCH (t:Tag), (s:Service) " +
            "WHERE id(s) = $serviceId AND id(t) = $tagId " +
            "CREATE (s)-[:HAS_TAG]->(t)")
    void createHasTagRelationship(Long serviceId, Long tagId);
}
