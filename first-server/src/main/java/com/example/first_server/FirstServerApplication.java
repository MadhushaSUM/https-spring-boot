package com.example.first_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController("/")
public class FirstServerApplication {

	@Autowired
	FirstService firstService;

	public static void main(String[] args) {
		SpringApplication.run(FirstServerApplication.class, args);
	}

	@GetMapping("hello")
	public String sayHello() {

		String data = firstService.getFromSecondServer();
		return "Hello from first server" + data;
	}

}
