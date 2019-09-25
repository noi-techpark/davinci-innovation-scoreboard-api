package it.bz.davinci.innovationscoreboard.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordRequest {
    @NotNull
    @Size(min = 8)
    private String password;
}
