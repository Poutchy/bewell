package com.taass.salon_service.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalTime;

@Data
@Node
public class TimeSlot {
    @Id
    @GeneratedValue
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;
}
