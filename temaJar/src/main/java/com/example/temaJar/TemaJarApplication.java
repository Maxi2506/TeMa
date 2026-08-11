package com.example.temaJar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.temaJar.models") // Para encontrar Usuario, Localidad, etc.
@EnableJpaRepositories(basePackages = "com.example.temaJar.repository") // Para encontrar tus interfaces
public class TemaJarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemaJarApplication.class, args);
	}

}
