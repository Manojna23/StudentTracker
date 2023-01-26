package org.group.dao;

import org.group.model.Student;

import java.math.BigInteger;
import java.util.List;

public interface StudentDao {
    int save(Student student);

    int update(BigInteger rollNo, Student student);

    List<Student> getAllStudents();

    Student getStudent(BigInteger rollNo);
}
