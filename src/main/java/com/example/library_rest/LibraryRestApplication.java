package com.example.library_rest;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// this annotation ensure spring boot performs 
// component scaning of all subpackages
// @configuration + @AutoConfiguration + @componentScan
@SpringBootApplication
public class LibraryRestApplication {

	public static void main(String[] args) {

		// TODO : move it to a @configuration file
		Dotenv dotenv = Dotenv.load();
		// Set environment variables as system properties
        System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(LibraryRestApplication.class, args);
	}

}
