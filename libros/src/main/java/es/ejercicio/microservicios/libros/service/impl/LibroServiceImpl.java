package es.ejercicio.microservicios.libros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import es.ejercicio.microservicios.libros.entity.Libro;
import es.ejercicio.microservicios.libros.repository.LibroRepository;
import es.ejercicio.microservicios.libros.service.LibroService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LibroServiceImpl implements LibroService {

	@Autowired
	private LibroRepository libroRepository;


	@Override
	public List<Libro> findByFavoriteTrue() {
		log.debug("Se obtienen todos los libros favoritos");
		return libroRepository.findByFavoriteTrue();
	}


	@Override
	public List<Libro> findAll() {
		log.debug("Se obtienen todos los libros");
		return libroRepository.findAll();
	}


	@Override
	public List<Libro> findByExample(Libro libro) {
		log.debug("Se obtienen todos los libros coincidentes");
		Example<Libro> exLibro = Example.of(libro);
		return libroRepository.findAll(exLibro);
	}

}
