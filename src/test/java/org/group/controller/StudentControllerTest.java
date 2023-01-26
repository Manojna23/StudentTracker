package org.group.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.group.model.Student;
import org.group.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    StudentService studentService;

    @Test
    public void testGetAllStudents() throws Exception {
        String endpoint = "/students/";
        List<Student> list = Arrays.asList(new Student(new BigInteger("1"),"Manojna", "Vijay", 10),
                new Student(new BigInteger("2"), "Akanksha", "Gupta", 12));

        Mockito.when(studentService.getAllStudents()).thenReturn(list);

        ResultActions actualResponse = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON));

        List<Student> expectedResponse = list;

        actualResponse
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    public void testSave() throws Exception {
        String endpoint = "/students/";
        Student student = new Student(new BigInteger("1"), "Manojna", "Vijay", 12);

        Mockito.when(studentService.save(student)).thenReturn(1);

        ResultActions actualResponse = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(student)));

        actualResponse
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(1)));
    }
}
