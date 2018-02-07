package es.ejercicio.microservicios.categorias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CategoriasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoriasApplication.class, args);
	}
}
