package com.taass.salon_service.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
@Data
public class Service {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private String duration;

    private boolean isAvailable;

    private Double discount;

    @Relationship(type = "HAS_TAG", direction = OUTGOING)
    private List<Tag> tags;

}
