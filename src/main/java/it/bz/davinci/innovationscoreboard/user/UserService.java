package it.bz.davinci.innovationscoreboard.user;

import it.bz.davinci.innovationscoreboard.user.dto.NewUserRequest;
import it.bz.davinci.innovationscoreboard.user.dto.UserResponse;
import it.bz.davinci.innovationscoreboard.user.jpa.UserRepository;
import it.bz.davinci.innovationscoreboard.user.jpa.UserRoleRepository;
import it.bz.davinci.innovationscoreboard.user.model.ApiUser;
import it.bz.davinci.innovationscoreboard.user.model.Role;
import it.bz.davinci.innovationscoreboard.user.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(@Valid NewUserRequest newUser) {
        ApiUser apiUser = new ApiUser();
        apiUser.setEmail(newUser.getEmail().trim().toLowerCase());
        apiUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        apiUser.setEnabled(true);
        final ApiUser createdUser = userRepository.save(apiUser);

        UserRole userRole = new UserRole();
        userRole.setEmail(createdUser.getEmail());
        userRole.setRole(Role.ROLE_USER);

        userRoleRepository.save(userRole);

        return UserMapper.INSTANCE.to(createdUser);
    }

}
