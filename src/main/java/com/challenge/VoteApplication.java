package com.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(exclude = { BatchAutoConfiguration.class })
public class VoteApplication {
	public static void main(String[] args) {
		SpringApplication.run(VoteApplication.class, args);
	}
}
