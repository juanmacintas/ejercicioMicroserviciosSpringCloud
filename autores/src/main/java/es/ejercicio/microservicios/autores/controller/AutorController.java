package es.ejercicio.microservicios.autores.controller;

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

import es.ejercicio.microservicios.autores.entity.Autor;
import es.ejercicio.microservicios.autores.service.AutorService;
import es.ejercicio.microservicios.dto.AutorDTO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/autores/")
public class AutorController {


    @Autowired
    private AutorService autorService;

    /** DozerMapper. */
    DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Retorna todos los autores
     * @return Listado de autores
     * @throws SQLException
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<AutorDTO> getAll() throws SQLException {

    	List<Autor> autores = autorService.findAll();
       	List<AutorDTO> autoresDTO = new ArrayList<AutorDTO>();
    	if (autores != null)
    	{
    		for (Autor autor : autores) {
    			AutorDTO autorDTO= (AutorDTO) mapper.map(autor, AutorDTO.class);
    			autoresDTO.add(autorDTO);
    		}

    	}
        return autoresDTO;

    }

    /**
     * Retorna el autor del id
     * @return Autor
     * @throws SQLException
     */
    @RequestMapping(value = "/getAutor/{id}", method = RequestMethod.GET)
    public ResponseEntity<AutorDTO> getAutor(@PathVariable("id") String id) throws SQLException {
    	Integer idAutor = 0;
    	try
    	{
    		idAutor = Integer.parseInt(id);
    	} catch (NumberFormatException ex) {
    		log.error("Se ha producido un error, el id no es un valor numerico:" + ex.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AutorDTO());
    	}
    	Autor editorial = autorService.findById(idAutor);
    	AutorDTO editorialDTO= (AutorDTO) mapper.map(editorial, AutorDTO.class);

       	return ResponseEntity.status(HttpStatus.OK).body(editorialDTO);

    }

    /**
     * Elimina el autor del id
     * @throws SQLException
     */
    @RequestMapping(value = "/deleteAutor/{id}", method = RequestMethod.DELETE)
    public HttpStatus deleteAutor(@PathVariable("id") String id) throws SQLException {
    	Integer idAutor = 0;
    	try
    	{
    		idAutor = Integer.parseInt(id);
    	} catch (NumberFormatException ex) {
    		log.error("Se ha producido un error, el id no es un valor numerico:" + ex.getMessage());
    		return HttpStatus.NOT_FOUND;
    	}
    	autorService.deleteById(idAutor);

       	return HttpStatus.OK;
    }


    /**
     * Añade un nuevo autor
     * @return Autor
     * @throws SQLException
     */
    @RequestMapping(value = "/nuevoAutor", method = RequestMethod.POST)
    public ResponseEntity<AutorDTO> nuevoAutor(@RequestBody AutorDTO input) throws SQLException {
    	log.debug("Se intenta insertar el autor:" + input);

    	Autor autor = Autor.builder().id(input.getId())
    											 .nombre(input.getNombre())
    											 .build();

    	Autor nuevoAutor = autorService.nuevoAutor(autor);
    	AutorDTO autorDTO= (AutorDTO) mapper.map(nuevoAutor, AutorDTO.class);

       	return ResponseEntity.status(HttpStatus.OK).body(autorDTO);

    }

}
