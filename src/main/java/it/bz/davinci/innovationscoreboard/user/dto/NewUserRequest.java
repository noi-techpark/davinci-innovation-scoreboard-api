package it.bz.davinci.innovationscoreboard.user.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class NewUserRequest {

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;
}
