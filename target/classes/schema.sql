DROP TABLE IF EXISTS STUDENT;

CREATE TABLE STUDENT (
    ROLLNO BIGINT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME VARCHAR(30) NOT NULL,
    LAST_NAME VARCHAR(30) NOT NULL,
    GRADE INT
);