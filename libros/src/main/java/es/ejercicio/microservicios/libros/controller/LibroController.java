package es.ejercicio.microservicios.libros.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.ejercicio.microservicios.dto.LibroDTO;
import es.ejercicio.microservicios.libros.entity.Libro;
import es.ejercicio.microservicios.libros.service.LibroService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/libros/")
class LibroController {


    @Autowired
    private LibroService libroService;

    /** DozerMapper. */
    DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Retorna todos los libros
     * @return Listado de libros
     * @throws SQLException
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<LibroDTO> getAll() throws SQLException {

    	List<Libro> libros = libroService.findAll();
       	List<LibroDTO> librosDTO = new ArrayList<LibroDTO>();
    	if (libros != null)
    	{
    		for (Libro libro : libros) {
    			LibroDTO libroDTO= (LibroDTO) mapper.map(libro, LibroDTO.class);
    			librosDTO.add(libroDTO);
    		}

    	}
        return librosDTO;


    }


    /**
     * Retorna todos los libros favoritos
     * @return Lista de libros favoritos
     * @throws SQLException
     */
    @RequestMapping(value = "/getFavoritos", method = RequestMethod.GET)
    public List<LibroDTO> getFavoritos() throws SQLException {

    	List<Libro> libros = libroService.findByFavoriteTrue();
       	List<LibroDTO> librosDTO = new ArrayList<LibroDTO>();
    	if (libros != null)
    	{
    		for (Libro libro : libros) {
    			LibroDTO libroDTO= (LibroDTO) mapper.map(libro, LibroDTO.class);
    			librosDTO.add(libroDTO);
    		}

    	}
        return librosDTO;

    }

    /**
     * Añade una nueva categoria
     * @return Categoria
     * @throws SQLException
     */
    @RequestMapping(value = "/getByExample", method = RequestMethod.POST)
    public List<LibroDTO> librosByExample(@RequestBody LibroDTO input) throws SQLException {
    	log.debug("Se obtienen los libros coincidentes con:" + input);

    	Libro libroInput = Libro.builder().id(null)
    								 .descripcion(input.getDescripcion())
    								 .titulo(input.getTitulo())
    								 .autor(input.getAutor())
    								 .categoria(input.getCategoria())
    								 .editorial(input.getEditorial())
    								 .build();

    	List<Libro> libros = libroService.findByExample(libroInput);
       	List<LibroDTO> librosDTO = new ArrayList<LibroDTO>();
    	if (libros != null)
    	{
    		for (Libro libro : libros) {
    			LibroDTO libroDTO= (LibroDTO) mapper.map(libro, LibroDTO.class);
    			librosDTO.add(libroDTO);
    		}

    	}
        return librosDTO;

    }

}
