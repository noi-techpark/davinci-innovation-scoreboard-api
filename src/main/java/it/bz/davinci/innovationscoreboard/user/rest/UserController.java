package it.bz.davinci.innovationscoreboard.user.rest;

import it.bz.davinci.innovationscoreboard.user.UserService;
import it.bz.davinci.innovationscoreboard.user.dto.NewUserRequest;
import it.bz.davinci.innovationscoreboard.user.dto.ResetPasswordRequest;
import it.bz.davinci.innovationscoreboard.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/new")
    public UserResponse createUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createUser(newUserRequest);
    }

    @PutMapping(value = "/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, Principal principal) {
        userService.resetPassword(principal.getName(), resetPasswordRequest);
    }
}
