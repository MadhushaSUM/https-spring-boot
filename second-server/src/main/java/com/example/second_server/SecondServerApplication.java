package com.example.second_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController("/")
public class SecondServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondServerApplication.class, args);
	}

	@GetMapping("/hi")
	public String sayHi() {
		return "Hi from second server";
	}
}
