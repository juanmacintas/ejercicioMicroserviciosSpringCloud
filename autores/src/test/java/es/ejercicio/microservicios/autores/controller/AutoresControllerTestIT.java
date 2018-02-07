package es.ejercicio.microservicios.autores.controller;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import es.ejercicio.microservicios.dto.AutorDTO;


/**
 * @author Juan Manuel Cintas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutoresControllerTestIT {

	@LocalServerPort
	private int port;

	private URL base;
	private URL baseById;
	private URL baseNuevoAutor;
	private URL baseEliminarAutor;

	@Autowired
	private TestRestTemplate template;

	private final String NOMBRE_SERVICIO = "autores/getAll/";
	private final String NOMBRE_SERVICIO_BY_ID= "autores/getAutor/1";
	private final String NOMBRE_SERVICIO_NUEVO= "autores/nuevoAutor";
	private final String NOMBRE_SERVICIO_ELIMINAR= "autores/deleteAutor/2";

	private final String STATUS_OK = "200";
	private final Integer NUM_TOTAL_AUTORES = 5;

	@Before
	public void setUp() throws Exception {
	     this.base = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO);
	     this.baseById = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_BY_ID);
	     this.baseNuevoAutor = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_NUEVO);
	     this.baseEliminarAutor = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_ELIMINAR);
	}

	@Test
	public void getAutorById() throws Exception {


	     ResponseEntity<AutorDTO> response = template.getForEntity(baseById.toString(), AutorDTO.class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     AutorDTO autor = (AutorDTO) response.getBody();

	     assertEquals(1, autor.getId());

	 }


	@Test
	public void nuevoAutor() throws Exception {
		Integer total = getTotal();

		Gson gson = new Gson();

		AutorDTO autor = AutorDTO.builder().id(6)
						.nombre("Autor 6")
						.build();
		String json = gson.toJson(autor);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json,headers);

	     ResponseEntity<AutorDTO> response = template.postForEntity(baseNuevoAutor.toString(),entity,
	    		 AutorDTO.class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     assertEquals(6,response.getBody().getId());

	     testSelectAll(total + 1);

	 }

	@Test
	public void eliminarAutorById() throws Exception {

		Integer total = getTotal();

	     template.delete(baseEliminarAutor.toString());


	     testSelectAll(total - 1);

	 }

	private void testSelectAll(Integer totalEsperado) {
	     ResponseEntity<Object[]> response = template.getForEntity(base.toString(), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(totalEsperado.intValue(), objects.length);

	}

	private int getTotal() {
	     ResponseEntity<Object[]> response = template.getForEntity(base.toString(), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     return objects.length;
	}

}
