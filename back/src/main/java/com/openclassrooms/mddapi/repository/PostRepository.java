package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Post;
 import  org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("""
    select p
      from Post p
      join p.subject s
      join Subscription sub on sub.subjects = s
     where sub.user.id = :userId
  """)
    List<Post> findFeedByUser(
            @Param("userId") Integer userId,
            Sort sort
    );

    Optional<Post> findByIdAndSubjectId(Integer postId, Integer subjectId);
}
