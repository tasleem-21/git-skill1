package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student save(Student s) {
        return repo.save(s);
    }

    public Student getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Student update(Long id, Student s) {
        Student existing = repo.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(s.getName());
            existing.setEmail(s.getEmail());
            existing.setCourse(s.getCourse());
            return repo.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}