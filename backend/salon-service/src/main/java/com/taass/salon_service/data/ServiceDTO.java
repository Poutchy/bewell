package com.taass.salon_service.data;

import com.taass.salon_service.model.Tag;
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
    private boolean isAvailable;
    private Double discount;
    private List<Tag> tags;

}
