package com.taass.salon_service.repository;

import com.taass.salon_service.model.Service;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ServiceRepository extends Neo4jRepository<Service, Long> {
}
