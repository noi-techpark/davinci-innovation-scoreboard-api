package it.bz.davinci.innovationscoreboard.profile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1")
public class ProfileController {

    @GetMapping(value = "/me")
    public String getMe() {
        return "{\"email\":\"user@example.com\"}";
    }

}
