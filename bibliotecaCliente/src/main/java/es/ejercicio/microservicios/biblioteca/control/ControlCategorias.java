package es.ejercicio.microservicios.biblioteca.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import es.ejercicio.microservicios.biblioteca.cliente.ClienteCategorias;
import es.ejercicio.microservicios.dto.CategoriaDTO;


@Component
public class ControlCategorias {

	@Autowired
	ClienteCategorias clienteCategorias;

	@HystrixCommand(fallbackMethod="failObtenerCategoria")
	public CategoriaDTO obtenerCategoria(String id) {
		return clienteCategorias.obtenerCategoria(id);
	}


	@HystrixCommand(fallbackMethod="failNuevaCategoria")
    public ResponseEntity<CategoriaDTO>  nuevaCategoria(@RequestBody CategoriaDTO input) {
	   return clienteCategorias.nuevaCategoria(input);
   }

	@HystrixCommand(fallbackMethod="failEliminarCategoria")
	public void eliminarCategoria(String id) {
		clienteCategorias.eliminarCategoria(id);
	}

	public CategoriaDTO failObtenerCategoria(String id, Throwable t) {
		t.printStackTrace();

        return CategoriaDTO.builder().id(0).nombre("").build();
    }

	public ResponseEntity<CategoriaDTO> failNuevaCategoria(CategoriaDTO input, Throwable t) {
		t.printStackTrace();

        return ResponseEntity
        		.status(HttpStatus.NOT_FOUND)
        		.body(CategoriaDTO.builder().id(0).nombre("").build());
    }

	public void failEliminarCategoria(String id, Throwable t) {
		t.printStackTrace();
    }

}
