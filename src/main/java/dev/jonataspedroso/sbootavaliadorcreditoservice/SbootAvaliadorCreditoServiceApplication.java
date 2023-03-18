package dev.jonataspedroso.sbootavaliadorcreditoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SbootAvaliadorCreditoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbootAvaliadorCreditoServiceApplication.class, args);
	}

}