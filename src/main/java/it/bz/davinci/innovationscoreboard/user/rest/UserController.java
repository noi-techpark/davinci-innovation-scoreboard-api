package it.bz.davinci.innovationscoreboard.user.rest;

import it.bz.davinci.innovationscoreboard.user.UserService;
import it.bz.davinci.innovationscoreboard.user.dto.NewUserRequest;
import it.bz.davinci.innovationscoreboard.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/new")
    public UserResponse createUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createUser(newUserRequest);
    }
}
