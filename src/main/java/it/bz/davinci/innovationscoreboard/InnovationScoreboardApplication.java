package it.bz.davinci.innovationscoreboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass=true)
public class InnovationScoreboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnovationScoreboardApplication.class, args);
    }

}
