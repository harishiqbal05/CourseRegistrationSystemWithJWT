package com.example.SpringJwt.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseDTO {
    private Long userId;
    private String username;
    private Long courseId;
    private String courseName;
}
