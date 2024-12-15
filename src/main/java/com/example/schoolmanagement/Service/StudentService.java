package com.example.schoolmanagement.Service;

import com.example.schoolmanagement.ApiResponse.ApiException;
import com.example.schoolmanagement.DTO.CourseDTO;
import com.example.schoolmanagement.DTO.StudentDTO;
import com.example.schoolmanagement.Model.Course;
import com.example.schoolmanagement.Model.Student;
import com.example.schoolmanagement.Model.Teacher;
import com.example.schoolmanagement.Repository.CourseRepository;
import com.example.schoolmanagement.Repository.StudentRepository;
import com.example.schoolmanagement.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    public List<StudentDTO> get (){
        List<Student> students= studentRepository.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<CourseDTO> courseDTOS =new ArrayList<>();
        for (Student s:students){
            for (Course c:s.getCourses()){
                Teacher teachers =  c.getTeacher();

                CourseDTO courseDTO= new CourseDTO(c.getName(),teachers.getName());
                courseDTOS.add(courseDTO);
            }
            StudentDTO studentDTO= new StudentDTO(s.getName(),s.getAge(),s.getMajor(),courseDTOS);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }


    public void add (Student student){
        studentRepository.save(student);
    }


    public void Update  (Integer id , Student student){
        Student old = studentRepository.findStudentById(id);
        if (old==null){
            throw new ApiException("student id not found");
        }
        old.setName(student.getName());
        old.setAge(student.getAge());
        old.setMajor(student.getMajor());
        studentRepository.save(old);

    }


    public void delete (Integer id ){
        Student student= studentRepository.findStudentById(id);
        if (student==null){
            throw new ApiException("student id not found");
        }

        studentRepository.delete(student);
    }

    public void assignStudentToCourse (Integer student_id,Integer course_id){
Student student= studentRepository.findStudentById(student_id);
Course course= courseRepository.findCourseById(course_id);
if (course==null || student== null){
    throw new ApiException("can not assign");
}
student.getCourses().add(course);
course.getStudents().add(student);
courseRepository.save(course);
studentRepository.save(student);
    }


    public void changeStudentMajorAndDropCourses (Integer student_id , String newMajor){
        Student student = studentRepository.findStudentById(student_id);
        if (student==null){
            throw new ApiException("student not found");
        }
        student.setMajor(newMajor);
        student.getCourses().clear();
        studentRepository.save(student);
    }
}
