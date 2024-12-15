package com.example.schoolmanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeacherDTO {
    private String name;
    private Integer age;
    private String email;

    private List<String> courseName;


}
