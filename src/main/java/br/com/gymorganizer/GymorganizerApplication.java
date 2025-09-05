package br.com.gymorganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GymorganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymorganizerApplication.class, args);
	}

}
