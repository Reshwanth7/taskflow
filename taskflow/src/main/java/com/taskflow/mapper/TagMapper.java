package com.taskflow.mapper;

import com.taskflow.domain.Tag;
import com.taskflow.dto.TagRequestDTO;
import com.taskflow.dto.TagResponseDTO;

public class TagMapper {

    public static Tag toEntity(TagRequestDTO dto) {
        Tag tag = new Tag();
        tag.setName(dto.name());
        tag.setColor(dto.color());
        return tag;
    }

    public static TagResponseDTO toDTO(Tag tag) {
        return new TagResponseDTO(
                tag.getId(),
                tag.getName(),
                tag.getColor()
        );
    }
}

