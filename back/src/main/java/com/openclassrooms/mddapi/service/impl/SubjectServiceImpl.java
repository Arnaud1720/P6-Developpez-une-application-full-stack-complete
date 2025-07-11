package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.SubjectDto;
import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import com.openclassrooms.mddapi.entity.Subjects;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }


    @Override
    public SubjectDto save(SubjectDto subjectDto) {
        Subjects entity = subjectMapper.toEntity(subjectDto);
        Subjects saved = subjectRepository.save(entity);
        return subjectMapper.toDto(saved);
    }
    @Override
    public List<SubjectDto> findAll() {
        List<Subjects> subjects = subjectRepository.findAll();
        return subjects.stream().map(subjectMapper::toDto).collect(toList());
    }
}
