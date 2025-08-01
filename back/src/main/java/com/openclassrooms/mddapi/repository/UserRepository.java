package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
 Optional<User> findByFirstname(String firstname);
 Optional<User> findByEmail(String email);
 Optional<User> findByUsername(String username);

}
