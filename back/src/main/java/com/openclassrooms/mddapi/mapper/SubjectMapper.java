package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.SubjectDto;
import org.mapstruct.Mapper;

import com.openclassrooms.mddapi.entity.Subjects;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectDto toDto(Subjects subject);
    Subjects toEntity(SubjectDto subjectDto);

}
