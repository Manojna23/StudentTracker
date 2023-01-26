package org.group.dao;

import org.group.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDaoImpl implements StudentDao {

//    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public StudentDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_INTO_STUDENT = "INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, GRADE) VALUES (:firstName, :lastName, :grade)";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM STUDENT";
    private static final String UPDATE_STUDENT = "UPDATE STUDENT SET FIRST_NAME=:firstName, LAST_NAME=:lastName, GRADE=:grade WHERE ROLL_NO=:rollNo";
    private static final String SELECT_BY_ID = "SELECT * FROM STUDENT WHERE ROLL_NO=:rollNo";
    private static final String COUNT_RECORDS = "SELECT COUNT(*) FROM STUDENT WHERE ROLL_NO=:rollNo";
    private static final String STUDENTS_WITH_GRADE="SELECT FIRST_NAME, GRADE FROM STUDENT";

    @Override
    public int save(Student student) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("firstName", student.getFirstName());
        mapSqlParameterSource.addValue("lastName", student.getLastName());
        mapSqlParameterSource.addValue("grade", student.getGrade());
        return jdbcTemplate.update(INSERT_INTO_STUDENT, mapSqlParameterSource);
    }

    @Override
    public int update(BigInteger rollNo, Student student) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("firstName", student.getFirstName());
        mapSqlParameterSource.addValue("lastName", student.getLastName());
        mapSqlParameterSource.addValue("grade", student.getGrade());
        mapSqlParameterSource.addValue("rollNo", rollNo);
        return jdbcTemplate.update(UPDATE_STUDENT, mapSqlParameterSource);
    }

    @Override
    public List<Student> getAllStudents() {
        return jdbcTemplate.query(SELECT_ALL_STUDENTS, new BeanPropertyRowMapper<Student>(Student.class));
    }

    @Override
    @Cacheable(value = "students", key="#rollNo")
    public Student getStudent(BigInteger rollNo) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("rollNo", rollNo);
        return jdbcTemplate.query(SELECT_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<Student>(Student.class)).get(0);
    }

    public int getNoOfRecords(BigInteger rollNo) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("rollNo", rollNo);
        int count = 0;
        count = jdbcTemplate.queryForObject(COUNT_RECORDS, mapSqlParameterSource,int.class);
        return count;
    }

    public Map<String, Integer> studentWithGrade() {
        return jdbcTemplate.query(STUDENTS_WITH_GRADE, new UniqueMapper());
    }

    public static class UniqueMapper implements ResultSetExtractor<Map<String, Integer>> {

        @Override
        public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<String, Integer> map = new HashMap<>();
            while(rs.next()) {
                String firstName = rs.getString(0);
                Integer grade = rs.getInt(1);
                map.put(firstName, grade);
            }
            return map;
        }
    }
}
