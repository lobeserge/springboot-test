package com.practice.springboottest.controller;

import com.practice.springboottest.dto.StudentDTO;
import com.practice.springboottest.dto.StudentUpdateDTO;
import com.practice.springboottest.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private  StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO){
        return new ResponseEntity(studentService.addStudent(studentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudent(){
        return new ResponseEntity(studentService.getAllStudent(),HttpStatus.OK);
    }

    @GetMapping("id/{studentId}")
    public ResponseEntity<StudentDTO> getOneStudent(@PathVariable Long studentId){
        return new ResponseEntity<>(studentService.getOneStudent(studentId),HttpStatus.OK);
    }

    @DeleteMapping("delete/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long studentId,
                                           @RequestBody StudentUpdateDTO studentUpdateDTO){
        return new ResponseEntity<>
                (studentService.updateStudent(studentUpdateDTO,studentId),HttpStatus.OK);
    }
}
