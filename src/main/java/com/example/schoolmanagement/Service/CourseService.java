package com.example.schoolmanagement.Service;

import com.example.schoolmanagement.ApiResponse.ApiException;
import com.example.schoolmanagement.DTO.CourseDTO;
import com.example.schoolmanagement.DTO.StudentDTO;
import com.example.schoolmanagement.Model.Course;
import com.example.schoolmanagement.Model.Student;
import com.example.schoolmanagement.Model.Teacher;
import com.example.schoolmanagement.Repository.CourseRepository;
import com.example.schoolmanagement.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
private  final TeacherRepository teacherRepository;


    public List<CourseDTO> get (){
        List<Course> courses= courseRepository.findAll();
        List<CourseDTO> courseDTOS =new ArrayList<>();
        for (Course c : courses){
            CourseDTO courseDTO= new CourseDTO(c.getName(),c.getTeacher().getName());
            courseDTOS.add(courseDTO);
        }
        return courseDTOS ;
    }

    public void  addCourse (Course course){
        courseRepository.save(course);
    }


    public void update (Integer id ,Course course){
        Course old = courseRepository.findCourseById(id);
        if (old==null){
            throw new ApiException("id not found");
        }
        old.setName(course.getName());
        courseRepository.save(old);
    }


    public void delete (Integer id ){
        Course course = courseRepository.findCourseById(id);
        if (course==null){
            throw new ApiException("id not found");
        }
        courseRepository.delete(course);
    }

    public String  getTeacher (Integer course_id){
        Course course =courseRepository.findCourseById(course_id);
        if (course==null){
            throw new ApiException("id not found");
        }

        return  course.getTeacher().getName();
    }


    public List<StudentDTO> getStudentByCourseId (Integer course_id){
        Course course =courseRepository.findCourseById(course_id);
        if (course==null){
            throw new ApiException("course not found");
        }
        List<StudentDTO> studentDTOS= new ArrayList<>();
        List<CourseDTO> courseDTOS =new ArrayList<>();
        for (Student s:course.getStudents()){
            for(Course c:s.getCourses()){
               CourseDTO courseDTO = new CourseDTO(c.getName(),c.getTeacher().getName());
               courseDTOS.add(courseDTO);
            }
            StudentDTO studentDTO =new StudentDTO(s.getName(),s.getAge(),s.getMajor(),courseDTOS);
            studentDTOS.add(studentDTO);
        }return studentDTOS;
    }

}
