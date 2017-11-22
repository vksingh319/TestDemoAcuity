package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringAutoPilotApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAutoPilotApiDemoApplication.class, args);
	}
}
