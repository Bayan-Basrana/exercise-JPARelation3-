package com.example.schoolmanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudentDTO {

    private String name;
    private Integer age;
    private String major;

private List<CourseDTO> courseDTOS;
}
