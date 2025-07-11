package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository  extends JpaRepository<Subjects, Integer> {

}
