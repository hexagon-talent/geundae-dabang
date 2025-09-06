package com.gundaero.alley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AlleyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlleyApplication.class, args);
	}

}
