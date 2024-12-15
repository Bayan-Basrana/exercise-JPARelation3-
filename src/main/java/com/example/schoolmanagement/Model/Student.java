package com.example.schoolmanagement.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name is mandatory")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;


    @NotNull(message = "age is mandatory")
    @Column(columnDefinition = "int not null")
    private Integer age;

    @NotEmpty(message = "major is mandatory")
    @Column(columnDefinition = "varchar(50) not null")
    private String major;

@ManyToMany(mappedBy = "students")
    private Set<Course> courses;

}
