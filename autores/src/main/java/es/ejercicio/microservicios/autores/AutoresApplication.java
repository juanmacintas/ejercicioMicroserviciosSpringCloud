package es.ejercicio.microservicios.autores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AutoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoresApplication.class, args);
	}
}
