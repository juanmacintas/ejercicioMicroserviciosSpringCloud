package es.ejercicio.microservicios.gestionconfiguracion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class GestionConfiguracionApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionConfiguracionApplication.class, args);
	}
}
