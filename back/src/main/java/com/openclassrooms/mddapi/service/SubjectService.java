package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    SubjectDto save(SubjectDto subjectDto);
    List<SubjectDto> findAll();
}
