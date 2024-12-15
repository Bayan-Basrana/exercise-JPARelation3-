package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.ApiResponse.ApiResponse;
import com.example.schoolmanagement.Model.Course;
import com.example.schoolmanagement.Model.Student;
import com.example.schoolmanagement.Service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/get")
    public ResponseEntity get (){
        return ResponseEntity.status(200).body(studentService.get());
    }


    @PostMapping("/add")
    public ResponseEntity add (@RequestBody @Valid Student student ){
        studentService.add(student);
        ;        return ResponseEntity.status(200).body(new ApiResponse("student added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update (@PathVariable Integer id , @RequestBody @Valid Student student ){
        studentService.Update(id,student);
        return ResponseEntity.status(200).body(new ApiResponse("Student updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete (@PathVariable Integer id){
        studentService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Student deleted successfully"));
    }

    @PutMapping("/assignStudentToCourse/{student_id}/{course_id}")
    public ResponseEntity assignStudentToCourse (@PathVariable Integer student_id,@PathVariable Integer course_id){
        studentService.assignStudentToCourse(student_id,course_id);
        return ResponseEntity.status(200).body(new ApiResponse("assign successfully"));
    }

    @PutMapping("/changeStudentMajorAndDropCourses/{student_id}/{newMajor}")
    public ResponseEntity changeStudentMajorAndDropCourses (@PathVariable Integer student_id ,@PathVariable String newMajor){
        studentService.changeStudentMajorAndDropCourses(student_id,newMajor);
        return ResponseEntity.status(200).body(new ApiResponse("change successfully"));
    }

}
