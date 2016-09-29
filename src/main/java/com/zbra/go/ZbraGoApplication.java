package com.zbra.go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.zbra.go")
@EnableAutoConfiguration
public class ZbraGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbraGoApplication.class, args);
	}

}
