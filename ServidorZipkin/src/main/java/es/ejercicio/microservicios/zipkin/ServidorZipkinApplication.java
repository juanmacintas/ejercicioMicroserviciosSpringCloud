package es.ejercicio.microservicios.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ServidorZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServidorZipkinApplication.class, args);
	}
}
