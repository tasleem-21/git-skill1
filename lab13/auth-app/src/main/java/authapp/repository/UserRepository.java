package authapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import authapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}