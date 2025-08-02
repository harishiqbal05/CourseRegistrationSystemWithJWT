package com.example.SpringJwt.Service;

import com.example.SpringJwt.Model.Course;
import com.example.SpringJwt.Model.User;
import com.example.SpringJwt.Repository.CourseRepository;
import com.example.SpringJwt.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
//
//    public List<Course> getAllCourses() {
//        return courseRepository.findAll();
//    }

    public String registerCourse(String username, Long courseId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        user.getCourses().add(course);
        userRepository.save(user);
        return "Course registered successfully";
    }
}
