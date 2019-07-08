package it.bz.davinci.innovationscoreboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class InnovationScoreboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnovationScoreboardApplication.class, args);
	}

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

}
