package es.ejercicio.microservicios.biblioteca.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import es.ejercicio.microservicios.biblioteca.cliente.ClienteLibros;
import es.ejercicio.microservicios.dto.LibroDTO;


@Component
public class ControlLibros {

	@Autowired
	ClienteLibros clienteLibros;

	@HystrixCommand(fallbackMethod="failObtenerLibros")
	public  List<LibroDTO> obtenerLibros() {
		return clienteLibros.obtenerLibros();
	}


	@HystrixCommand(fallbackMethod="failObtenerLibrosFavoritos")
	public  List<LibroDTO> obtenerLibrosFavoritos() {
		return clienteLibros.obtenerLibrosFavoritos();
	}

	@HystrixCommand(fallbackMethod="failObtenerLibrosByExample")
	public  List<LibroDTO> obtenerLibrosByExample(LibroDTO example) {
		return clienteLibros.obtenerLibrosByExample(example);
	}


	public List<LibroDTO> failObtenerLibros(Throwable t) {
		t.printStackTrace();

		return new ArrayList<LibroDTO>() ;
    }

	public List<LibroDTO> failObtenerLibrosFavoritos(Throwable t) {
		t.printStackTrace();

		return new ArrayList<LibroDTO>() ;
    }

	public List<LibroDTO>  failObtenerLibrosByExample(LibroDTO input, Throwable t) {
		t.printStackTrace();

        return new ArrayList<LibroDTO>() ;
    }

}
