package es.ejercicio.microservicios.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServidorEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServidorEurekaApplication.class, args);
	}
}
