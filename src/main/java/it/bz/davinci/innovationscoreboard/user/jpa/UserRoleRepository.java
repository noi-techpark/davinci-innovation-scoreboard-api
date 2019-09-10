package it.bz.davinci.innovationscoreboard.user.jpa;

import it.bz.davinci.innovationscoreboard.user.model.UserRole;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    void deleteAllByEmail(String email);
}
