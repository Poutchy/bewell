package com.taass.salon_service.repository;

import com.taass.salon_service.model.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TagRepository extends Neo4jRepository<Tag, Long> {

}
