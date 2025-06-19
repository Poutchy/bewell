package com.taass.salon_service.repository;

import com.taass.salon_service.model.Salon;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SalonRepository extends Neo4jRepository<Salon, Long> {
}
