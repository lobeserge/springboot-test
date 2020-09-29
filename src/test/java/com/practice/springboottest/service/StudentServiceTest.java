package com.practice.springboottest.service;

import com.practice.springboottest.dto.StudentDTO;
import com.practice.springboottest.dto.StudentUpdateDTO;
import com.practice.springboottest.model.Student;
import com.practice.springboottest.repository.StudentRepository;
import com.practice.springboottest.service.ServiceImpl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    StudentService studentService;

    @BeforeEach
    public void  setUp(){
        studentService = new StudentServiceImpl(studentRepository);
    }

    @Test
    public void should_addStudent_successfully(){

        //define the expected body
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setSchool("css");
        studentDTO.setAge(12);
        studentDTO.setName("serge");

        //Load DTO to repository object
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setSchool(studentDTO.getSchool());
        student.setAge(studentDTO.getAge());


        //return student whenever save is called on repo
        when(studentRepository.save(any())).thenReturn(student);

        //load dto to service and verify saved values
        StudentDTO studentDTO1 = studentService.addStudent(studentDTO);

        //verify repo
        verify(studentRepository).save(any(Student.class));

        assertThat(studentDTO1).isNotNull();
        assertThat(studentDTO1.getAge()).isEqualTo(12);
        assertThat(studentDTO1.getName()).isEqualTo("serge");


    }


    @Test
    public void shouldReturnFindAllWhenOnAllStudent(){
        //create the expected list object - you could create a single object and convert to list
        List<Student> students = new ArrayList<>();
        students.add(new Student("lobe",12,"css"));
        students.add(new Student("serge",14,"gss"));

        //add stub on repo
        when(studentRepository.findAll()).thenReturn(students);

        //call actual service
        List<StudentDTO> studentDTOS = studentService.getAllStudent();
        verify(studentRepository).findAll();
        //assertion
        assertThat(studentDTOS.get(0).getName()).isEqualTo("lobe");
        assertThat(studentDTOS.size()).isEqualTo(students.size());
        assertThat(studentDTOS.size()).isNotNull();
        assertThat(studentDTOS.size()).isGreaterThan(0);
    }


    @Test
    public void should_return_user_on_getOneStudent(){
         Long Id = 1L;
         Student student = new Student("lobe",12,"css");
         when(studentRepository.findById(Id)).thenReturn(Optional.of(student));
         StudentDTO studentDTO = studentService.getOneStudent(Id);
         assertThat(studentDTO).isNotNull();
         assertThat(studentDTO.getName()).isEqualTo("lobe");
    }

    @Test
    public void deleteStudent_should_delete_student(){
        final  Long Id = 1L;
        studentService.deleteStudent(Id);
        studentService.deleteStudent(Id);
        verify(studentRepository,times(2)).deleteById(Id);
    }


    @Test
    public  void  update_user_works(){

        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO();
        studentUpdateDTO.setName("serge");

            long Id = 1L;
        Student student = new Student(studentUpdateDTO.getName(),15,"lbt");
        student.setId(Id);

        when(studentRepository.findById(Id)).thenReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.updateStudent(studentUpdateDTO, Id);

        verify(studentRepository).findById((long)1);
        assertThat(studentDTO).isNotNull();
        assertThat(studentDTO.getName()).isEqualTo("serge");
        assertThat(studentDTO.getAge()).isEqualTo(15);
    }
}
