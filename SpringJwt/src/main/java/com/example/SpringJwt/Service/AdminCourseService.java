package com.example.SpringJwt.Service;

import com.example.SpringJwt.Dto.UserCourseDTO;
import com.example.SpringJwt.Model.Course;

import java.util.List;

public interface AdminCourseService {
    Course createCourse(Course course);
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);
    Course getCourseById(Long id);
    List<Course> getAllCourses();
    List<UserCourseDTO> getAllUserCourseRegistrations();

    void deleteUser(Long id);
}
