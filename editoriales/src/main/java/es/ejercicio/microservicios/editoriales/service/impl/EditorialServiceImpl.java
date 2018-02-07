package es.ejercicio.microservicios.editoriales.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ejercicio.microservicios.editoriales.entity.Editorial;
import es.ejercicio.microservicios.editoriales.repository.EditorialRepository;
import es.ejercicio.microservicios.editoriales.service.EditorialService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EditorialServiceImpl implements EditorialService {

	@Autowired
	private EditorialRepository editorialRepository;


	@Override
	public List<Editorial> findAll() {
		log.debug("Se obtienen todas las editoriales");
		return editorialRepository.findAll();
	}


	@Override
	public Editorial findById(Integer id) {
		log.debug("Se obtiene la editorial con id:" + id);
		return editorialRepository.findOne(id);
	}


	@Override
	public void deleteById(Integer id) {
		log.debug("Se elimina la editorial con id:" + id);
		editorialRepository.delete(id);

	}


	@Override
	public Editorial nuevaEditorial(Editorial editorial) {
		log.debug("Se añade la editorial:" + editorial);
		return editorialRepository.save(editorial);
	}

}
