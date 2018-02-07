package es.ejercicio.microservicios.autores.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ejercicio.microservicios.autores.entity.Autor;
import es.ejercicio.microservicios.autores.repository.AutorRepository;
import es.ejercicio.microservicios.autores.service.AutorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AutorServiceImpl implements AutorService {

	@Autowired
	private AutorRepository autorRepository;


	@Override
	public List<Autor> findAll() {
		log.debug("Se obtienen todos los autores.");

		return autorRepository.findAll();
	}


	@Override
	public Autor findById(Integer id) {
		log.debug("Se obtiene el autor con id:" + id);
		return autorRepository.findOne(id);
	}


	@Override
	public void deleteById(Integer id) {
		log.debug("Se elimina el autor con id:" + id);
		 autorRepository.delete(id);

	}


	@Override
	public Autor nuevoAutor(Autor autor) {
		log.debug("Se añade el autor:" + autor);
		return autorRepository.save(autor);
	}

}
