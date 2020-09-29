package com.practice.springboottest.service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.practice.springboottest.dto.StudentDTO;
import com.practice.springboottest.dto.StudentUpdateDTO;
import com.practice.springboottest.model.Student;
import com.practice.springboottest.repository.StudentRepository;
import com.practice.springboottest.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public StudentDTO addStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setAge(studentDTO.getAge());
        student.setName(studentDTO.getName());
        student.setSchool(studentDTO.getSchool());
        studentRepository.save(student);

        StudentDTO studentDTO1 = new StudentDTO();
        studentDTO1.setName(student.getName());
        studentDTO1.setAge(student.getAge());
        studentDTO1.setSchool(student.getSchool());
        return studentDTO1;
    }

    @Override
    public List<StudentDTO> getAllStudent() {
        List<Student> student = studentRepository.findAll();
        List<StudentDTO> studentDTO = new ArrayList<>();
        for(Student student1: student){
            StudentDTO studentDTO1 = new StudentDTO();
            studentDTO1.setSchool(student1.getSchool());
            studentDTO1.setAge(student1.getAge());
            studentDTO1.setName(student1.getName());
            studentDTO.add(studentDTO1);
        }
        return  studentDTO;
    }

    @Override
    public StudentDTO getOneStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).get();
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setAge(student.getAge());
        studentDTO.setSchool(student.getSchool());
        return  studentDTO;
    }

    @Override
    public void deleteStudent(Long studentId) {
         studentRepository.deleteById(studentId);
    }

    @Override
    public StudentDTO updateStudent(StudentUpdateDTO studentDTO, Long id) {
        Optional<Student> student = studentRepository.findById(id);
        Student student1 = new Student();
         if(student.isPresent()){
             student1 = student.get();
             student1.setName(studentDTO.getName());
             studentRepository.save(student1);
         }

        StudentDTO studentDTO2 = new StudentDTO();
        studentDTO2.setName(student1.getName());
        studentDTO2.setAge(student1.getAge());
        studentDTO2.setSchool(student1.getSchool());
        return  studentDTO2;

    }
}
