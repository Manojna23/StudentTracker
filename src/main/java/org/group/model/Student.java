package org.group.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    private BigInteger rollNo;
    @JsonAlias({"FN"})
    private String firstName;
    @JsonAlias({"LN"})
    private String lastName;
    private int grade;
}
