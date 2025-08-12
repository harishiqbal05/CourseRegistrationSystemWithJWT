package com.example.SpringJwt.Controller;

import com.example.SpringJwt.Model.Course;
import com.example.SpringJwt.Model.User;
import com.example.SpringJwt.Repository.CourseRepository;
import com.example.SpringJwt.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;


    //  Register for a course (authenticated user)
    @PostMapping("/courses/{courseId}/register")
    public ResponseEntity<String> registerCourse(
            @PathVariable Long courseId,
            Authentication authentication
    ) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        user.getCourses().add(course);
        userRepository.save(user);

        return ResponseEntity.ok("Registered for course: " + course.getCourseName());
    }
}
