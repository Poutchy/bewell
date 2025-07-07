package com.taass.salon_service.repository;

import com.taass.salon_service.model.TimeSlot;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TimeSlotRepository extends Neo4jRepository<TimeSlot, Long> {
}
