package es.ejercicio.microservicios.editoriales.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.ejercicio.microservicios.dto.AutorDTO;
import es.ejercicio.microservicios.dto.CategoriaDTO;
import es.ejercicio.microservicios.dto.EditorialDTO;
import es.ejercicio.microservicios.editoriales.entity.Editorial;
import es.ejercicio.microservicios.editoriales.service.EditorialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/editoriales/")
@Api(value = "EditorialController", description="Operaciones sobre las Editoriales de los libros de la Biblioteca")
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
    @ApiOperation(value = "Retorna todos las editoriales",
	  notes = "Retorna todos las editoriales almacenados en base de datos",
	  response = EditorialDTO.class,
	  responseContainer = "List")
@ApiResponses(value = {@ApiResponse(code = 200, message = "Editoriales retornadas correctamente")})
    public List<EditorialDTO> getAll(Authentication authentication) throws SQLException {

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
    @ApiOperation(value = "Retorna una Editorial",
	  notes = "Retorna la Editorial del id especificado",
	  response = AutorDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Editorial no encontrada"),
    					   @ApiResponse(code = 200, message = "Editorial encontrada")}
    						)
    public ResponseEntity<EditorialDTO> getEditorial(Authentication authentication,@ApiParam(name = "id", value = "Id del Autor a buscar", required = true)@PathVariable("id") String id) throws SQLException {
    	Integer idEditorial = 0;
    	try
    	{
    		idEditorial = Integer.parseInt(id);
    	} catch (NumberFormatException ex) {
    		log.error("Se ha producido un error, el id no es un valor numerico:" + ex.getMessage());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EditorialDTO());
    	}
    	Editorial editorial = editorialService.findById(idEditorial);

    	if (editorial != null)
    	{
    		EditorialDTO editorialDTO= (EditorialDTO) mapper.map(editorial, EditorialDTO.class);

    		return ResponseEntity.status(HttpStatus.OK).body(editorialDTO);
    	} else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EditorialDTO());
    	}


    }

    /**
     * Elimina la editorial del id
     * @throws SQLException
     */
    @RequestMapping(value = "/deleteEditorial/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Elimina una Editorial",
			notes = "Elimina la Editorial del id especificado",
			response = HttpStatus.class)
    public HttpStatus deleteEditorial(@ApiParam(name = "id", value = "Id del Autor a eliminar", required = true)@PathVariable("id") String id) throws SQLException {
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
    @ApiOperation("Inserta o actualiza en base de datos una Editorial")
    public ResponseEntity<EditorialDTO> nuevaEditorial(@ApiParam(name = "editorialDTO", value = "Editorial a insertar/actualizar", required = true)@RequestBody CategoriaDTO input) throws SQLException {
    	log.debug("Se intenta insertar la editorial:" + input);

    	Editorial editorial = Editorial.builder().id(input.getId())
    											 .nombre(input.getNombre())
    											 .build();

    	Editorial nuevaEditorial = editorialService.nuevaEditorial(editorial);
    	EditorialDTO editorialDTO= (EditorialDTO) mapper.map(nuevaEditorial, EditorialDTO.class);

       	return ResponseEntity.status(HttpStatus.OK).body(editorialDTO);

    }

}
