package com.example.SpringJwt.Controller;

import com.example.SpringJwt.Dto.CourseDTO;
import com.example.SpringJwt.Dto.UserCourseDTO;
import com.example.SpringJwt.Model.Course;
import com.example.SpringJwt.Service.AdminCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminCourseController {

    private final AdminCourseService courseService;

    // ✅ Create Course
    @PostMapping("/admin/courses")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.createCourse(course);
        return ResponseEntity.ok(CourseDTO.fromEntity(savedCourse));
    }

    // ✅ Update Course
    @PutMapping("/admin/courses/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updated = courseService.updateCourse(id, course);
        return ResponseEntity.ok(CourseDTO.fromEntity(updated));
    }

    // ✅ Delete Course
    @DeleteMapping("/admin/courses/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }

    // ✅ Get Course by ID
    @GetMapping("/admin/courses/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(CourseDTO.fromEntity(course));
    }

    // ✅ Get All Courses
    @GetMapping("/courses/all")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> allCourses = courseService.getAllCourses().stream()
                .map(CourseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allCourses);
    }

    // ✅ Get All User-Course Registrations (Admin Only)
    @GetMapping("/admin/user-courses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserCourseDTO>> getAllUserCourseRegistrations() {
        List<UserCourseDTO> registrations = courseService.getAllUserCourseRegistrations();
        return ResponseEntity.ok(registrations);
    }

    @DeleteMapping("admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            courseService.deleteUser(id); // your service method
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }


}

