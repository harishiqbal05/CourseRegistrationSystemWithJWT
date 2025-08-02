package com.example.SpringJwt.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class UserCourseResponseDTO {
    private Long userId;
    private List<String> courseNames;
}
