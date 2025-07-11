package com.taass.salon_service.data;

import lombok.Data;
import java.time.Duration;
import java.util.List;

@Data
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Duration duration;
    private boolean isAvailable = true;
    private Double discount;
    private List<TagDTO> tags;
}
