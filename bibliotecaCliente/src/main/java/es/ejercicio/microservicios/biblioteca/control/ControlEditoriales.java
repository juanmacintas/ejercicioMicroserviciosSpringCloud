package es.ejercicio.microservicios.biblioteca.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import es.ejercicio.microservicios.biblioteca.cliente.ClienteEditoriales;
import es.ejercicio.microservicios.dto.EditorialDTO;


@Component
public class ControlEditoriales {

	@Autowired
	ClienteEditoriales clienteEditoriales;

	@HystrixCommand(fallbackMethod="failObtenerEditorial")
	public EditorialDTO obtenerEditorial(String id) {
		return clienteEditoriales.obtenerEditorial(id);
	}


	@HystrixCommand(fallbackMethod="failNuevaEditorial")
    public ResponseEntity<EditorialDTO>  nuevaEditorial(@RequestBody EditorialDTO input) {
	   return clienteEditoriales.nuevoEditorial(input);
   }

	@HystrixCommand(fallbackMethod="failEliminarEditorial")
	public void eliminarEditorial(String id) {
		clienteEditoriales.eliminarEditorial(id);
	}

	public EditorialDTO failObtenerEditorial(String id, Throwable t) {
		t.printStackTrace();
        return EditorialDTO.builder().id(0).nombre("NO DISPONIBLE").build();
    }

	public ResponseEntity<EditorialDTO> failNuevaEditorial(EditorialDTO input, Throwable t) {
        return ResponseEntity
        		.status(HttpStatus.NOT_FOUND)
        		.body(EditorialDTO.builder().id(0).nombre("NO DISPONIBLE").build());
    }

	public void failEliminarEditorial(String id, Throwable t) {
		t.printStackTrace();
    }

}
