package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubjectDto;
import com.openclassrooms.mddapi.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.security.auth.Subject;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@RequestBody @Valid SubjectDto subjectDto) {
        SubjectDto subjectDtoSaved= subjectService.save(subjectDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(subjectDtoSaved.toString())
                .toUri();
        Map<String,Object> body = new HashMap<>();
        body.put("Object",subjectDtoSaved.toString());
        body.put("Location",location);
        return ResponseEntity.created(location).body(body);
    }
    @GetMapping("/all")
    public ResponseEntity<List<SubjectDto>> getAll() {
        List<SubjectDto> subjectDtos = subjectService.findAll();
        return ResponseEntity.ok(subjectDtos);
    }
}
