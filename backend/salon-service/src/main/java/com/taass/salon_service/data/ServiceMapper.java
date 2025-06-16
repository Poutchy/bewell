package com.taass.salon_service.data;

import com.taass.salon_service.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(target = "duration", source = "duration", qualifiedByName = "toDuration")
    ServiceDTO toDTO(Service service);

    @Mapping(target = "duration", source = "duration", qualifiedByName = "fromDuration")
    Service toEntity(ServiceDTO serviceDTO);

    @Named("toDuration")
    default Duration toDuration(String duration) {
        return duration != null ? Duration.parse(duration) : null;
    }

    @Named("fromDuration")
    default String fromDuration(Duration duration) {
        return duration != null ? duration.toString() : null;
    }

}
