package com.klu.skill9.controller;

import com.klu.skill9.exception.InvalidInputException;
import com.klu.skill9.exception.StudentNotFoundException;
import com.klu.skill9.model.Student;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable int id) {

        if (id <= 0) {
            throw new InvalidInputException("Invalid ID! ID must be positive.");
        }

        if (id != 1) {
            throw new StudentNotFoundException("Student with ID " + id + " not found.");
        }

        return new Student(1, "Tasleem", "CSE");
    }
}