package es.ejercicio.microservicios.biblioteca.cliente;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.ejercicio.microservicios.dto.AutorDTO;

@FeignClient(name="autores")
public interface ClienteAutores {

   @RequestMapping(path = "/autores/getAutor/{id}",
    		method = RequestMethod.GET)
    public AutorDTO obtenerAutor(@PathVariable("id") String id);

   @RequestMapping(path = "/autores/nuevoAutor",
   		method = RequestMethod.POST)
   public ResponseEntity<AutorDTO>  nuevoAutor(@RequestBody AutorDTO input);


   @RequestMapping(path = "/autores/deleteAutor/{id}",
   		method = RequestMethod.DELETE)
   public void eliminarAutor(@PathVariable("id") String id);

}
