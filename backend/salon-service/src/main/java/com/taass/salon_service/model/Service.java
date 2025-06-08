package com.taass.salon_service.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.temporal.TemporalAmount;
import java.util.List;

@Node
public class Service {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private TemporalAmount duration;

    private boolean isAvailble;

    private Double discount;

    @Relationship(type = "HAS_TAG")
    private List<Tag> tags;

}
