package it.bz.davinci.innovationscoreboard.user.jpa;

import it.bz.davinci.innovationscoreboard.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {}
