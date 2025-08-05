package com.example.SpringJwt.Service;

import com.example.SpringJwt.Dto.UserCourseDTO;
import com.example.SpringJwt.Dto.UserCourseResponseDTO;
import com.example.SpringJwt.Model.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import com.example.SpringJwt.Model.User;
import com.example.SpringJwt.Repository.CourseRepository;
import com.example.SpringJwt.Repository.UserRepository;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@Log4j2
//@Slf4j
@Service
public class AdminCourseServiceImpl implements AdminCourseService {

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setCourseduration(course.getCourseduration());
        existingCourse.setCoursefee(course.getCoursefee());
        
        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserCourseDTO> getAllUserCourseRegistrations() {
        return userRepository.findAllUserCourseMappings()
                .stream()
                .map(this::convertToUserCourseDTO)
                .collect(Collectors.toList());
    }

    private UserCourseDTO convertToUserCourseDTO(Object[] row) {
        return new UserCourseDTO(
                ((Number) row[0]).longValue(), // userId
                (String) row[1],              // username
                ((Number) row[2]).longValue(), // courseId
                (String) row[3]               // courseName
        );
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getCourses().clear(); // removes entries from join table
        userRepository.delete(user);
    }


}
