package com.example.schoolmanagement.Service;

import com.example.schoolmanagement.ApiResponse.ApiException;
import com.example.schoolmanagement.DTO.TeacherDTO;
import com.example.schoolmanagement.Model.Address;
import com.example.schoolmanagement.Model.Course;
import com.example.schoolmanagement.Model.Teacher;
import com.example.schoolmanagement.Repository.AddressRepository;
import com.example.schoolmanagement.Repository.CourseRepository;
import com.example.schoolmanagement.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final AddressRepository addressRepository;
    private final CourseRepository courseRepository;

   public List<TeacherDTO> getTeacher (){
        List<Teacher> teachers= teacherRepository.findAll();
        List<TeacherDTO> teacherDTOS= new ArrayList<>();
        List<String> curseNames= new ArrayList<>();
        for (Teacher t :teachers){
            for (Course c:t.getCourses()){
             String  curseName=  c.getName();
             curseNames.add(curseName);
            }

            TeacherDTO teacherDTO= new TeacherDTO(t.getName(),t.getAge(),t.getEmail(),curseNames);
            teacherDTOS.add(teacherDTO);
        }
        return teacherDTOS ;
    }

    public void addTeacher (Teacher teacher) {
        teacherRepository.save(teacher);
    }


    public void updateTeacher (Integer id , Teacher teacher){
        Teacher old = teacherRepository.findTeacherById(id);
        if (old==null){
            throw new ApiException("Teacher id not found");
        }
        old.setName(teacher.getName());
        old.setAge(teacher.getAge());
        old.setSalary(teacher.getSalary());
        old.setEmail(teacher.getEmail());
        teacherRepository.save(old);
    }

    public void delete (Integer id ){
        Teacher teacher = teacherRepository.findTeacherById(id);
        if (teacher==null){
            throw new ApiException("Teacher id not found");
        }
        Address address=addressRepository.findAddressById(id);
        teacher.setAddress(null);
        addressRepository.delete(address);
        teacherRepository.delete(teacher);
    }


    public TeacherDTO getTeacherByTeacherId (Integer id){
        Teacher teacher= teacherRepository.findTeacherById(id);
        if (teacher==null){
            throw new ApiException("Teacher id not found");
        }
        List<Course> courses = courseRepository.findAll();
        List<String> curseNames= new ArrayList<>();
        for (Course c: courses){
            String curseName = c.getName();
            curseNames.add(curseName);
        }
        TeacherDTO teacherDTO= new TeacherDTO(teacher.getName(), teacher.getAge(), teacher.getEmail(),curseNames);
        return teacherDTO;
    }

    public void assignTeacherToCourse (Integer teacher_id ,Integer course_id){
        Teacher teacher = teacherRepository.findTeacherById(teacher_id);
        Course course = courseRepository.findCourseById(course_id);

        if (teacher == null || course==null){
            throw new ApiException("can not assign");
        }

        course.setTeacher(teacher);
        courseRepository.save(course);
    }
}
