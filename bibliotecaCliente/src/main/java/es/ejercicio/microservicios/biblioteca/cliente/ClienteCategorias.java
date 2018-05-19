package es.ejercicio.microservicios.biblioteca.cliente;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.ejercicio.microservicios.biblioteca.config.OAuth2FeignAutoConfiguration;
import es.ejercicio.microservicios.dto.CategoriaDTO;

@FeignClient(name="categorias",
	configuration = {OAuth2FeignAutoConfiguration.class})
public interface ClienteCategorias {

   @RequestMapping(path = "/categorias/getCategoria/{id}",
    		method = RequestMethod.GET)
    public CategoriaDTO obtenerCategoria(@PathVariable("id") String id);

   @RequestMapping(path = "/categorias/nuevaCategoria",
   		method = RequestMethod.POST)
   public ResponseEntity<CategoriaDTO>  nuevaCategoria(@RequestBody CategoriaDTO input);


   @RequestMapping(path = "/categorias/deleteCategoria/{id}",
   		method = RequestMethod.DELETE)
   public void eliminarCategoria(@PathVariable("id") String id);

}
