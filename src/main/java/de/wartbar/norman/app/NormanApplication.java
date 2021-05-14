package de.wartbar.norman.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@ComponentScan({"de.wartbar.norman.app","de.wartbar.norman.controller", "de.wartbar.norman.spring.data.persistence"})
@EntityScan(basePackages = "de.wartbar.norman.spring.data.persistence")
@EnableJpaRepositories("de.wartbar.norman.spring.data.persistence")
@Configuration
@SpringBootApplication
public class NormanApplication {

	public static void main(String[] args) {
		SpringApplication.run(NormanApplication.class, args);
	}

}
