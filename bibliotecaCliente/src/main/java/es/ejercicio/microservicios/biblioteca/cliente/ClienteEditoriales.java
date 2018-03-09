package es.ejercicio.microservicios.biblioteca.cliente;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.ejercicio.microservicios.biblioteca.config.OAuth2FeignAutoConfiguration;
import es.ejercicio.microservicios.dto.EditorialDTO;

@FeignClient(name="editoriales",
		configuration = {OAuth2FeignAutoConfiguration.class})
public interface ClienteEditoriales {

   @RequestMapping(path = "/editoriales/getEditorial/{id}",
    		method = RequestMethod.GET)
    public EditorialDTO obtenerEditorial(@PathVariable("id") String id);

   @RequestMapping(path = "/editoriales/nuevaEditorial",
   		method = RequestMethod.POST)
   public ResponseEntity<EditorialDTO>  nuevoEditorial(@RequestBody EditorialDTO input);


   @RequestMapping(path = "/editoriales/deleteEditorial/{id}",
   		method = RequestMethod.DELETE)
   public void eliminarEditorial(@PathVariable("id") String id);

}
