package com.example.SpringJwt.Service;

import com.example.SpringJwt.Dto.UserCourseDTO;
import com.example.SpringJwt.Exception.ResourceNotFoundException;
import com.example.SpringJwt.Model.Course;
import com.example.SpringJwt.Model.User;
import com.example.SpringJwt.Repository.CourseRepository;
import com.example.SpringJwt.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class AdminCourseServiceImpl implements AdminCourseService {

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public Course createCourse(Course course) {
        log.info("Creating new course: {}", course.getCourseName());
        try {
            Course savedCourse = courseRepository.save(course);
            log.info("Course created successfully with ID: {}", savedCourse.getId());
            return savedCourse;
        } catch (Exception e) {
            log.error("Error creating course: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create course: " + e.getMessage());
        }
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        log.info("Updating course with ID: {}", id);
        try {
            Course existingCourse = courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
            
            log.debug("Updating course details - Old: {}, New: {}", existingCourse, course);
            existingCourse.setCourseName(course.getCourseName());
            existingCourse.setCourseduration(course.getCourseduration());
            existingCourse.setCoursefee(course.getCoursefee());
            
            Course updatedCourse = courseRepository.save(existingCourse);
            log.info("Course updated successfully with ID: {}", id);
            return updatedCourse;
            
        } catch (ResourceNotFoundException e) {
            log.warn("Update failed - Course not found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error updating course with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update course: " + e.getMessage());
        }
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Deleting course with ID: {}", id);
        try {
            if (!courseRepository.existsById(id)) {
                throw new ResourceNotFoundException("Course not found with id: " + id);
            }
            courseRepository.deleteById(id);
            log.info("Course deleted successfully with ID: {}", id);
        } catch (ResourceNotFoundException e) {
            log.warn("Delete failed - Course not found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error deleting course with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete course: " + e.getMessage());
        }
    }

    @Override
    public Course getCourseById(Long id) {
        log.debug("Fetching course with ID: {}", id);
        try {
            return courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        } catch (ResourceNotFoundException e) {
            log.warn("Course not found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error fetching course with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch course: " + e.getMessage());
        }
    }

    @Override
    public List<Course> getAllCourses() {
        log.debug("Fetching all courses");
        try {
            List<Course> courses = courseRepository.findAll();
            log.debug("Fetched {} courses", courses.size());
            return courses;
        } catch (Exception e) {
            log.error("Error fetching all courses: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch courses: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserCourseDTO> getAllUserCourseRegistrations() {
        log.debug("Fetching all user course registrations");
        try {
            List<UserCourseDTO> registrations = userRepository.findAllUserCourseMappings()
                    .stream()
                    .map(this::convertToUserCourseDTO)
                    .collect(Collectors.toList());
            log.debug("Fetched {} user course registrations", registrations.size());
            return registrations;
        } catch (Exception e) {
            log.error("Error fetching user course registrations: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch user course registrations: " + e.getMessage());
        }
    }

    private UserCourseDTO convertToUserCourseDTO(Object[] row) {
        return new UserCourseDTO(
                ((Number) row[0]).longValue(), // userId
                (String) row[1],              // username
                ((Number) row[2]).longValue(), // courseId
                (String) row[3]               // courseName
        );
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Deleting user with ID: {}", userId);
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

            log.debug("Clearing course associations for user ID: {}", userId);
            user.getCourses().clear(); // removes entries from join table
            userRepository.delete(user);
            log.info("User deleted successfully with ID: {}", userId);
            
        } catch (ResourceNotFoundException e) {
            log.warn("Delete failed - User not found with ID: {}", userId);
            throw e;
        } catch (Exception e) {
            log.error("Error deleting user with ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete user: " + e.getMessage());
        }
    }


}
