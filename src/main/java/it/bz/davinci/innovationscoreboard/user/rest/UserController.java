package it.bz.davinci.innovationscoreboard.user.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import it.bz.davinci.innovationscoreboard.user.UserService;
import it.bz.davinci.innovationscoreboard.user.dto.NewUserRequest;
import it.bz.davinci.innovationscoreboard.user.dto.ResetPasswordRequest;
import it.bz.davinci.innovationscoreboard.user.dto.UserResponse;
import it.bz.davinci.innovationscoreboard.user.model.Role;
import it.bz.davinci.innovationscoreboard.utils.rest.CollectionResponse;
import it.bz.davinci.innovationscoreboard.utils.security.IsAdmin;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

import static it.bz.davinci.innovationscoreboard.user.model.Role.ROLE_ADMIN;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @IsAdmin
    @ApiOperation(value = "List all users. Admins only.", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "")
    public CollectionResponse<UserResponse> findAll() {
        return userService.findAll();
    }

    @IsAdmin
    @ApiOperation(value = "Create a new user. Admins only.", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/new")
    public UserResponse createUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createUser(newUserRequest);
    }

    @IsAdmin
    @ApiOperation(value = "Delete a user by id. Admins only.", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Reset your password.", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, Principal principal) {
        userService.resetPassword(principal.getName(), resetPasswordRequest);
    }

    @IsAdmin
    @ApiOperation(value = "Reset the password of a user. Admin only.", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/reset-password/{id}")
    public void resetPassword(@PathVariable("id") Integer id, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        userService.resetPassword(id, resetPasswordRequest);
    }
}
