package com.practice.springboottest.controller;

import com.practice.springboottest.dto.StudentDTO;
import com.practice.springboottest.dto.StudentUpdateDTO;
import com.practice.springboottest.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    private StudentController studentController;
    @BeforeEach
    public void setUp(){
        studentController = new StudentController(studentService);
    }

    @Test
    public void add_student_calls_studentService(){
        //create the required request body object
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("lobe");
        studentDTO.setAge(19);
        studentDTO.setSchool("fet");
        //stub expected return type from service call- could create new object if not same as above
        when(studentService.addStudent(any())).thenReturn(studentDTO);
        //Perform actual controller call
        ResponseEntity<StudentDTO> response = studentController.addStudent(studentDTO);
        //verify if service was called
        verify(studentService).addStudent(studentDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isInstanceOf(StudentDTO.class);

    }

    @Test
    public void getAllStudent_Success(){
        List<StudentDTO> studentDTOList = new ArrayList<>();
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setSchool("fhs");
        studentDTOList.add(studentDTO);

        when(studentService.getAllStudent()).thenReturn(studentDTOList);

        ResponseEntity<?> response = studentController.getAllStudent();
        verify(studentService).getAllStudent();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(new ArrayList<StudentDTO>().getClass());


    }

    @Test
    public void getOneStudent_works(){
        long Id = 1L;

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("lobe");

        when(studentService.getOneStudent(Id)).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.getOneStudent(Id);

        verify(studentService).getOneStudent(Id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("lobe");

    }

    @Test
    public void deleteStudent_works(){
        long Id  = 1L;
        ResponseEntity<?> response = studentController.deleteStudent(Id);
        verify(studentService).deleteStudent(Id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void updateStudent_works(){
        long Id = 1L;
        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO();
        studentUpdateDTO.setName("raphael");

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("raphael");

        when(studentService.updateStudent(studentUpdateDTO,Id)).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response  = studentController.updateStudent(Id,studentUpdateDTO);
        verify(studentService).updateStudent(studentUpdateDTO,Id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(StudentDTO.class);
    }
}
