package org.group.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.group.dao.StudentDaoImpl;
import org.group.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class StudentService {

//    private StudentDaoImpl studentDao;
    @Autowired
    StudentDaoImpl studentDao;

//    public StudentService(StudentDaoImpl studentDao) {
//        this.studentDao = studentDao;
//    }

    @Value("${value}")
    int value;

    public int save(Student student) {
        return studentDao.save(student);
    }

    public int update(BigInteger rollNo, Student student) {
        return studentDao.update(rollNo, student);
    }

    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    public Student getStudent(BigInteger rollNo) {
        return studentDao.getStudent(rollNo);
    }

    public boolean isValidRollNo(BigInteger rollNo) {
        int count = studentDao.getNoOfRecords(rollNo);
        System.out.println("The value is:"+value);
        if(count > 0) {
            return true;
        }
        return false;
    }

    public List<Student> getContentUsingHttpUrlConnection() throws MalformedURLException, IOException {
        //using HttpURLConnection
        URL url = new URL("http://localhost:8080/students/");
        List<Student> result = new ArrayList<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            Student[] resultTemp = objectMapper.readValue(responseStream, Student[].class);
            result = Arrays.asList(resultTemp);

            System.out.println(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /*@Scheduled(fixedDelay = 1000)
    // fixedDelay - duration between the end of last execution and start of the next execution.
    public void scheduleFixedDelayTask() {
        System.out.println("Fixed Delay Task - "+System.currentTimeMillis()/1000);
    }

//    @Scheduled(fixedRate = 1000)
    // fixedRate - use this when execution of each task is independent
    // https://www.baeldung.com/spring-scheduled-tasks
    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void scheduleFixedRateTask() {
        System.out.println("Fixed Rate Task - "+System.currentTimeMillis()/1000);
    }

    @Scheduled(cron = "${cron.expression}")
    public void scheduleTaskUsingCronExpression() {
        System.out.println("Schedule task using cron job - "+System.currentTimeMillis()/1000);
    }*/
}
