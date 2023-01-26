package org.group.controller;

import org.group.model.Student;
import org.group.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;
//    private StudentService studentService;
//
//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }

    @PostMapping("/")
    public int save(@RequestBody Student student) {
        return studentService.save(student);
    }

    @GetMapping("/")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PutMapping("/update/{id}")
    public int updateStudent(@PathVariable BigInteger id, @RequestBody Student student) throws Exception {
        if(id.compareTo(new BigInteger("5")) == 0) {
            throw new IOException();
        }
        return studentService.update(id, student);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<String> getStudent(@PathVariable BigInteger id) {
        boolean isValidRollNo = studentService.isValidRollNo(id);
        if(isValidRollNo) {
            Student student = studentService.getStudent(id);
            return new ResponseEntity<>("The student is:" + student, HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no student with rollno:"+id, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/old")
    public List<Student> getResultUsingOldMethod() throws Exception {
        return studentService.getContentUsingHttpUrlConnection();
    }
}
