package es.ejercicio.microservicios.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.ejercicio.microservicios.dto.AutorDTO;
import es.ejercicio.microservicios.dto.CategoriaDTO;
import es.ejercicio.microservicios.dto.EditorialDTO;
import es.ejercicio.microservicios.dto.LibroBibliotecaDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestClient {

	@Value("${endpoint.LibrosBiblioteca}")
	private String WS_URI_LIST_LIBROS;

	@Value("${endpoint.LibrosFavoritosBiblioteca}")
	private String WS_URI_LIST_LIBROS_FAVORITOS;

	@Value("${endpoint.AutoresBiblioteca}")
	private String WS_URI_LIST_AUTORES;

	@Value("${endpoint.EditorialesBiblioteca}")
	private String WS_URI_LIST_EDITORIALES;

	@Value("${endpoint.CategoriasBiblioteca}")
	private String WS_URI_LIST_CATEGORIAS;

	@Autowired
	AppConfiguration config;

	public List<LibroBibliotecaDTO> obtenerLibrosBiblioteca() throws RuntimeException {
		log.debug("Inicio obtenerLibrosBiblioteca");
		LibroBibliotecaDTO[] mensaje = config.restTemplate().getForObject(WS_URI_LIST_LIBROS, LibroBibliotecaDTO[].class);
		return Arrays.asList(mensaje);
	}

	public List<LibroBibliotecaDTO> obtenerLibrosFavoritosBiblioteca() throws RuntimeException {
		log.debug("Inicio obtenerLibrosFavoritosBiblioteca");
		LibroBibliotecaDTO[] mensaje = config.restTemplate().getForObject(WS_URI_LIST_LIBROS_FAVORITOS, LibroBibliotecaDTO[].class);
		return Arrays.asList(mensaje);
	}

	public List<AutorDTO> obtenerAutoresBiblioteca() throws RuntimeException {
		log.debug("Inicio obtenerAutoresBiblioteca");
		AutorDTO[] mensaje = config.restTemplate().getForObject(WS_URI_LIST_AUTORES, AutorDTO[].class);
		return Arrays.asList(mensaje);
	}

	public List<CategoriaDTO> obtenerCategoriasBiblioteca() throws RuntimeException {
		log.debug("Inicio obtenerAutoresBiblioteca");
		CategoriaDTO[] mensaje = config.restTemplate().getForObject(WS_URI_LIST_CATEGORIAS, CategoriaDTO[].class);
		return Arrays.asList(mensaje);
	}

	public List<EditorialDTO> obtenerEditorialesBiblioteca() throws RuntimeException {
		log.debug("Inicio obtenerAutoresBiblioteca");
		EditorialDTO[] mensaje = config.restTemplate().getForObject(WS_URI_LIST_EDITORIALES, EditorialDTO[].class);
		return Arrays.asList(mensaje);
	}


}
