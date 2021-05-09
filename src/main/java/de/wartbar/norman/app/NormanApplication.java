package de.wartbar.norman.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "de.wartbar.norman.controller")
@SpringBootApplication
public class NormanApplication {

	public static void main(String[] args) {
		SpringApplication.run(NormanApplication.class, args);
	}

}
