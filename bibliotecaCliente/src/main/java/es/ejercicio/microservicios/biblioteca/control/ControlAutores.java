package es.ejercicio.microservicios.biblioteca.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import es.ejercicio.microservicios.biblioteca.cliente.ClienteAutores;
import es.ejercicio.microservicios.dto.AutorDTO;


@Component
public class ControlAutores {

	@Autowired
	ClienteAutores clienteAutores;

	@HystrixCommand(fallbackMethod="failObtenerAutor")
	public AutorDTO obtenerAutor(String id) {
		return clienteAutores.obtenerAutor(id);
	}


	@HystrixCommand(fallbackMethod="failNuevoAutor")
    public ResponseEntity<AutorDTO>  nuevoAutor(@RequestBody AutorDTO input) {
	   return clienteAutores.nuevoAutor(input);
   }

	@HystrixCommand(fallbackMethod="failEliminarAutor")
	public void eliminarAutor(String id) {
		 clienteAutores.eliminarAutor(id);
	}

	public AutorDTO failObtenerAutor(String id, Throwable t) {
        return AutorDTO.builder().id(0).nombre("NO DISPONIBLE").build();
    }

	public ResponseEntity<AutorDTO> failNuevoAutor(AutorDTO input, Throwable t) {
        return ResponseEntity
        		.status(HttpStatus.NOT_FOUND)
        		.body(AutorDTO.builder().id(0).nombre("NO DISPONIBLE").build());
    }

	public void failEliminarAutor(String id, Throwable t) {
		t.printStackTrace();
    }

}
