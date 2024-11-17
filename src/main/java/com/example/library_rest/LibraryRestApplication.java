package com.example.library_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// this annotation ensure spring boot performs 
// component scaning of all subpackages
// @configuration + @AutoConfiguration + @componentScan
@SpringBootApplication
public class LibraryRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryRestApplication.class, args);
	}

}
