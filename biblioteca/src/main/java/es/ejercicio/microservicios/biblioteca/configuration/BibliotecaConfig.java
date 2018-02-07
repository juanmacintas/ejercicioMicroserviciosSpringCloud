package es.ejercicio.microservicios.biblioteca.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BibliotecaConfig {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}