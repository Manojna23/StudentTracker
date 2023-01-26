package org.group.dao;

import org.group.model.Student;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

@JdbcTest  //connects to H2 DB, Created a jdbcTemplate, uses data.sql and schema.sql from resources.
@ActiveProfiles("test")
public class StudentDaoImplTest {


    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    StudentDaoImpl underTest;

    @BeforeAll
    public static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")   //port to connect to H2
                .start();
    }
    @BeforeEach
    public void setUp() {
        this.underTest = new StudentDaoImpl(jdbcTemplate);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = underTest.getAllStudents();
        System.out.println(students);
        underTest.save(new Student(new BigInteger("2"), "Rishi", "Singhania", 10));
        List<Student> newStudents = underTest.getAllStudents();
        System.out.println(newStudents);
    }
}
