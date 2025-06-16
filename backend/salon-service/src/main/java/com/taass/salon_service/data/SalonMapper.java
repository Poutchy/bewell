package com.taass.salon_service.data;

import com.taass.salon_service.model.OpeningHour;
import com.taass.salon_service.model.Salon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SalonMapper {

    @Mapping(target = "openingHours", source = "openingHours", qualifiedByName = "openingHoursToStrings")
    Salon toEntity(SalonDTO dto);

    @Mapping(target = "openingHours", source = "openingHours", qualifiedByName = "stringsToOpeningHours")
    SalonDTO toDTO(Salon salon);

    @Named("stringsToOpeningHours")
    default List<OpeningHour> stringsToOpeningHours(List<String> openingHoursStrings) {
        if (openingHoursStrings == null) {
            return Collections.emptyList();
        }
        return openingHoursStrings.stream()
                .map(OpeningHour::fromFormattedString)
                .toList();
    }

    @Named("openingHoursToStrings")
    default List<String> openingHoursToStrings(List<OpeningHour> openingHours) {
        if (openingHours == null) {
            return Collections.emptyList();
        }
        return openingHours.stream()
                .map(oh -> {
                    if (oh.getOpen() == null || oh.getClose() == null) {
                        return oh.getDay().toString().substring(0,1) + oh.getDay().toString().substring(1).toLowerCase() + ": Closed";
                    } else {
                        // Format time back to "h:mm a" string
                        String openStr = oh.getOpen().format(OpeningHour.getTimeFormatter());
                        String closeStr = oh.getClose().format(OpeningHour.getTimeFormatter());
                        return oh.getDay().toString().substring(0,1) + oh.getDay().toString().substring(1).toLowerCase() + ": " + openStr + " - " + closeStr;
                    }
                })
                .toList();
    }
}
