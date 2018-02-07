package es.ejercicio.microservicios.editoriales.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.ejercicio.microservicios.dto.CategoriaDTO;
import es.ejercicio.microservicios.dto.EditorialDTO;
import es.ejercicio.microservicios.editoriales.entity.Editorial;
import es.ejercicio.microservicios.editoriales.service.EditorialService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/editoriales/")
public class EditorialController {


    @Autowired
    private EditorialService editorialService;

    /** DozerMapper. */
    DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Retorna todos las editoriales
     * @return Listado de editoriales
     * @throws SQLException
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<EditorialDTO> getAll() throws SQLException {

    	List<Editorial> editoriales = editorialService.findAll();
       	List<EditorialDTO> editorialesDTO = new ArrayList<EditorialDTO>();
    	if (editoriales != null)
    	{
    		for (Editorial editorial : editoriales) {
    			EditorialDTO editorialDTO= (EditorialDTO) mapper.map(editorial, EditorialDTO.class);
    			editorialesDTO.add(editorialDTO);
    		}

    	}
        return editorialesDTO;

    }

    /**
     * Retorna la editorial del id
     * @return Editorial
     * @throws SQLException
     */
    @RequestMapping(value = "/getEditorial/{id}", method = RequestMethod.GET)
    public ResponseEntity<EditorialDTO> getEditorial(@PathVariable("id") String id) throws SQLException {
    	Integer idEditorial = 0;
    	try
    	{
    		idEditorial = Integer.parseInt(id);
    	} catch (NumberFormatException ex) {
    		log.error("Se ha producido un error, el id no es un valor numerico:" + ex.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EditorialDTO());
    	}
    	Editorial editorial = editorialService.findById(idEditorial);
       	EditorialDTO editorialDTO= (EditorialDTO) mapper.map(editorial, EditorialDTO.class);

       	return ResponseEntity.status(HttpStatus.OK).body(editorialDTO);

    }

    /**
     * Elimina la editorial del id
     * @throws SQLException
     */
    @RequestMapping(value = "/deleteEditorial/{id}", method = RequestMethod.DELETE)
    public HttpStatus deleteEditorial(@PathVariable("id") String id) throws SQLException {
    	Integer idAutor = 0;
    	try
    	{
    		idAutor = Integer.parseInt(id);
    	} catch (NumberFormatException ex) {
    		log.error("Se ha producido un error, el id no es un valor numerico:" + ex.getMessage());
    		return HttpStatus.NOT_FOUND;
    	}
    	editorialService.deleteById(idAutor);

       	return HttpStatus.OK;
    }

    /**
     * Añade una nueva categoria
     * @return Categoria
     * @throws SQLException
     */
    @RequestMapping(value = "/nuevaEditorial", method = RequestMethod.POST)
    public ResponseEntity<EditorialDTO> nuevaEditorial(@RequestBody CategoriaDTO input) throws SQLException {
    	log.debug("Se intenta insertar la editorial:" + input);

    	Editorial editorial = Editorial.builder().id(input.getId())
    											 .nombre(input.getNombre())
    											 .build();

    	Editorial nuevaEditorial = editorialService.nuevaEditorial(editorial);
    	EditorialDTO editorialDTO= (EditorialDTO) mapper.map(nuevaEditorial, EditorialDTO.class);

       	return ResponseEntity.status(HttpStatus.OK).body(editorialDTO);

    }

}
