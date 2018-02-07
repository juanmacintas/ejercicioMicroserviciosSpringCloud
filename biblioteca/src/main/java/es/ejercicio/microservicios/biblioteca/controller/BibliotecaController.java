package es.ejercicio.microservicios.biblioteca.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.ejercicio.microservicios.biblioteca.control.ControlAutores;
import es.ejercicio.microservicios.biblioteca.control.ControlCategorias;
import es.ejercicio.microservicios.biblioteca.control.ControlEditoriales;
import es.ejercicio.microservicios.biblioteca.control.ControlLibros;
import es.ejercicio.microservicios.dto.AutorDTO;
import es.ejercicio.microservicios.dto.CategoriaDTO;
import es.ejercicio.microservicios.dto.EditorialDTO;
import es.ejercicio.microservicios.dto.LibroBibliotecaDTO;
import es.ejercicio.microservicios.dto.LibroDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/")
public class BibliotecaController {


	@Autowired
    private ControlAutores controlAutores;

	@Autowired
    private ControlCategorias controlCategorias;

	@Autowired
    private ControlEditoriales controlEditoriales;

	@Autowired
    private ControlLibros controlLibros;

    /** DozerMapper. */
    DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Retorna todos los libros
     * @return Listado de libros
     * @throws SQLException
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<LibroBibliotecaDTO> getAll() throws SQLException {


		List<LibroDTO> listaBiblioteca = controlLibros.obtenerLibros();
		return getListaBiblioteca(listaBiblioteca);
    }

    /**
     * Retorna todos los libros favoritos
     * @return Listado de libros favoritos
     * @throws SQLException
     */
    @RequestMapping(value = "/getAllFavoritos", method = RequestMethod.GET)
    public List<LibroBibliotecaDTO> getAllFavoritos() throws SQLException {


		List<LibroDTO> listaBiblioteca = controlLibros.obtenerLibrosFavoritos();

    	return getListaBiblioteca(listaBiblioteca);
    }

    /**
     * Elimina el Autor
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
    	LibroDTO libroAutor = LibroDTO.builder().autor(idAutor).build();
    	List<LibroDTO> listaBiblioteca = controlLibros.obtenerLibrosByExample(libroAutor);
    	if (listaBiblioteca.isEmpty()) {
    		log.debug("Se elimina el autor.");
    		controlAutores.eliminarAutor(idAutor.toString());
    		return HttpStatus.OK;
    	} else {
    		log.debug("No se puede eliminar el autor ya que tiene libros en la biblioteca.");
    		return HttpStatus.NOT_ACCEPTABLE;
    	}

    }


    private List<LibroBibliotecaDTO> getListaBiblioteca(List<LibroDTO> listaBiblioteca) {

    	List<LibroBibliotecaDTO> librosBiblioteca = new ArrayList<LibroBibliotecaDTO>();
    	log.debug("Total de Libros Obtenidos:" + listaBiblioteca.size());
    	for(LibroDTO libro : listaBiblioteca) {
    		log.debug("Libro:" + libro);
    		LibroBibliotecaDTO bibliotecaLibro = obtenerValoresLibro(libro);
    		librosBiblioteca.add(bibliotecaLibro);
    	}
    	return librosBiblioteca;
    }

  private LibroBibliotecaDTO obtenerValoresLibro(LibroDTO libro) {
	log.debug("Se obtienen los datos del libro:" + libro);
	CategoriaDTO categoria = controlCategorias.obtenerCategoria(libro.getCategoria().toString());
	EditorialDTO editorial = controlEditoriales.obtenerEditorial(libro.getEditorial().toString());
	AutorDTO autor = controlAutores.obtenerAutor(libro.getAutor().toString());

	return LibroBibliotecaDTO.builder()
						.id(libro.getId())
						.titulo(libro.getTitulo())
						.descripcion(libro.getDescripcion())
						.categoria(categoria.getNombre())
						.editorial(editorial.getNombre())
						.autor(autor.getNombre())
						.build();

  }

}
