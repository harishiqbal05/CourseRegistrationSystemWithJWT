package com.example.SpringJwt.Repository;

import com.example.SpringJwt.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);

  @Query("""
      SELECT u.id as userId, u.username as username, c.id as courseId, c.courseName as courseName
      FROM User u
      JOIN u.courses c
      ORDER BY u.id, c.id
      """)
  List<Object[]> findAllUserCourseMappings();
}