package skill12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill12.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}