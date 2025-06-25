package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.SubjectDto;
import org.mapstruct.Mapper;

import javax.security.auth.Subject;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectDto toDto(Subject subject);
    Subject toEntity(SubjectDto subjectDto);

}
