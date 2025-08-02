package com.example.SpringJwt.Dto;

import com.example.SpringJwt.Model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String courseName;
    private String courseduration;
    private String coursefee;
    private Set<Long> userIds;

    public static CourseDTO fromEntity(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getCourseName(),
                course.getCourseduration(),
                course.getCoursefee(),
                course.getUserIds()
        );
    }
}
