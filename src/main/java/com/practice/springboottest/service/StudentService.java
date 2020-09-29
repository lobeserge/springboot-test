package com.practice.springboottest.service;

import java.util.List;

import com.practice.springboottest.dto.StudentDTO;
import com.practice.springboottest.dto.StudentUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public interface  StudentService {

     StudentDTO addStudent(StudentDTO studentDTO);

     List<StudentDTO> getAllStudent();

     StudentDTO getOneStudent(Long studentId) ;

     void deleteStudent(Long studentId);

     StudentDTO updateStudent(StudentUpdateDTO studentDTO, Long studentId);


}

