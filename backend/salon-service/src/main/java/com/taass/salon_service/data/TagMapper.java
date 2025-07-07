package com.taass.salon_service.data;

import com.taass.salon_service.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDto(Tag tag);
    Tag toEntity(TagDTO tagDTO);
}
