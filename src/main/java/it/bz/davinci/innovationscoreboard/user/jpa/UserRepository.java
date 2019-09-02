package it.bz.davinci.innovationscoreboard.user.jpa;

import it.bz.davinci.innovationscoreboard.user.model.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApiUser, Integer> {
    Optional<ApiUser> findByEmail(String email);
}
