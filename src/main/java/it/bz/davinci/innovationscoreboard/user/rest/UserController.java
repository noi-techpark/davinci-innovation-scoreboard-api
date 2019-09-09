package it.bz.davinci.innovationscoreboard.user.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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

    @ApiOperation(value = "Create a new user. Admins only.", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/new")
    public UserResponse createUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createUser(newUserRequest);
    }

    @ApiOperation(value = "Reset your password.", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, Principal principal) {
        userService.resetPassword(principal.getName(), resetPasswordRequest);
    }
}
