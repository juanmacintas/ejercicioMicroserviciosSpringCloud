package es.ejercicio.microservicios.categorias.controller;

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

import es.ejercicio.microservicios.categorias.entity.Categoria;
import es.ejercicio.microservicios.categorias.service.CategoriaService;
import es.ejercicio.microservicios.dto.AutorDTO;
import es.ejercicio.microservicios.dto.CategoriaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@Api(value = "CategoriaController", description="Operaciones sobre las Categorías de los libros de la Biblioteca")
@RequestMapping(value = "/categorias/")
public class CategoriaController {


    @Autowired
    private CategoriaService categoriaService;

    /** DozerMapper. */
    DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Retorna todos las categorias
     * @return Listado de categorias
     * @throws SQLException
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna todas las categorías",
	  notes = "Retorna todos las categorías almacenados en base de datos",
	  response = CategoriaDTO.class,
	  responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Categorías retornados correctamente")})
    public List<CategoriaDTO> getAll() throws SQLException {

    	List<Categoria> categorias = categoriaService.findAll();
       	List<CategoriaDTO> autoresDTO = new ArrayList<CategoriaDTO>();
    	if (categorias != null)
    	{
    		for (Categoria categoria : categorias) {
    			CategoriaDTO categoriaDTO= (CategoriaDTO) mapper.map(categoria, CategoriaDTO.class);
    			autoresDTO.add(categoriaDTO);
    		}

    	}
        return autoresDTO;

    }

    /**
     * Retorna la categoria del id
     * @return Categoria
     * @throws SQLException
     */
    @RequestMapping(value = "/getCategoria/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna una Categoría",
	  notes = "Retorna la Categoría del id especificado",
	  response = CategoriaDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Categoría no encontrada"),
    					   @ApiResponse(code = 200, message = "Categoría encontrada")}
    						)
    public ResponseEntity<CategoriaDTO> getCategoria(@ApiParam(name = "id", value = "Id de la Categoría a buscar", required = true)@PathVariable("id") String id) throws SQLException {
    	Integer idAutor = 0;
    	try
    	{
    		idAutor = Integer.parseInt(id);
    	} catch (NumberFormatException ex) {
    		log.error("Se ha producido un error, el id no es un valor numerico:" + ex.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoriaDTO());
    	}
    	Categoria categoria = categoriaService.findById(idAutor);

    	if (categoria != null)
    	{
    		CategoriaDTO categoriaDTO= (CategoriaDTO) mapper.map(categoria, CategoriaDTO.class);

    		return ResponseEntity.status(HttpStatus.OK).body(categoriaDTO);
    	} else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoriaDTO());
    	}

    }

    /**
     * Elimina la categoria del id
     * @throws SQLException
     */
    @RequestMapping(value = "/deleteCategoria/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Elimina una Categoría",
		notes = "Elimina la Categoría del id especificado",
		response = HttpStatus.class)
    public HttpStatus deleteCategoria(@ApiParam(name = "id", value = "Id de la Categoría a eliminar", required = true) @PathVariable("id") String id) throws SQLException {
    	Integer idCategoria = 0;
    	try
    	{
    		idCategoria = Integer.parseInt(id);
    	} catch (NumberFormatException ex) {
    		log.error("Se ha producido un error, el id no es un valor numerico:" + ex.getMessage());
    		return HttpStatus.NOT_FOUND;
    	}
    	categoriaService.deleteById(idCategoria);

       	return HttpStatus.OK;
    }

    /**
     * Añade una nueva categoria
     * @return Categoria
     * @throws SQLException
     */
    @RequestMapping(value = "/nuevaCategoria", method = RequestMethod.POST)
    @ApiOperation("Inserta o actualiza en base de datos un Autor")
    public ResponseEntity<CategoriaDTO> nuevaCategoria(@ApiParam(name = "categoriaDTO", value = "Categoría a insertar/actualizar", required = true)@RequestBody CategoriaDTO input) throws SQLException {
    	log.debug("Se intenta insertar la categoria:" + input);

    	Categoria categoria = Categoria.builder().id(input.getId())
    											 .nombre(input.getNombre())
    											 .build();

    	Categoria nuevaCategoria = categoriaService.nuevaCategoria(categoria);
    	CategoriaDTO categoriaDTO= (CategoriaDTO) mapper.map(nuevaCategoria, CategoriaDTO.class);

       	return ResponseEntity.status(HttpStatus.OK).body(categoriaDTO);

    }

}
