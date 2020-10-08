package com.practice.springboottest.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.springboottest.dto.StudentDTO;
import com.practice.springboottest.dto.StudentUpdateDTO;
import com.practice.springboottest.model.Student;
import com.practice.springboottest.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;





@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;



    @Test
    public void shouldCreateStudent() throws Exception {
        //what is the expected input
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("le");
        studentDTO.setSchool("fet");
        studentDTO.setAge(30);

        //create the request builder
        RequestBuilder requestBuilder = post("/api/student/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studentDTO))
                .accept(MediaType.APPLICATION_JSON);

        //execute the actual request builder
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();


    }

    @Test
    public void shouldgetAllStudent() throws Exception {
        Student student = new Student("le",45,"nyoh");
        student.setId((long)1);
        studentRepository.save(student);

        RequestBuilder requestBuilder = get("/api/student/all")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldgetOneStudent() throws Exception{


        Student student = new Student("le",45,"nyoh");
        student.setId((long)1);
        studentRepository.save(student);

       RequestBuilder requestBuilder = get("/api/studen/"+1)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON);
       mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andReturn();

    }

    @Test
    public void shouldUpdateStudent() throws Exception{
        Student student = new Student("le",45,"nyoh");
        student.setId((long)1);
        studentRepository.save(student);

        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO();
        studentUpdateDTO.setName("kumba");

        RequestBuilder requestBuilder = put("/api/student/update/{studentId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studentUpdateDTO))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldDeleteStudent() throws Exception{
        Student student = new Student("le",45,"nyoh");
        student.setId((long)1);
        studentRepository.save(student);

        RequestBuilder requestBuilder = delete("/api/student/delete/{studentId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();

    }


}
