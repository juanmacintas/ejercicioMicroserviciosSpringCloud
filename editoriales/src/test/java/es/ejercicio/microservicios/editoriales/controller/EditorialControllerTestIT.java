package es.ejercicio.microservicios.editoriales.controller;

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
import es.ejercicio.microservicios.dto.EditorialDTO;


/**
 * @author Juan Manuel Cintas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EditorialControllerTestIT {

	@LocalServerPort
	private int port;

	private URL base;
	private URL baseById;
	private URL baseNuevaEditorial;
	private URL baseEliminarEditorial;

	@Autowired
	private TestRestTemplate template;

	private final String NOMBRE_SERVICIO = "editoriales/getAll/";
	private final String NOMBRE_SERVICIO_BY_ID= "editoriales/getEditorial/1";
	private final String NOMBRE_SERVICIO_NUEVO= "editoriales/nuevaEditorial";
	private final String NOMBRE_SERVICIO_ELIMINAR= "editoriales/deleteEditorial/2";

	private final String STATUS_OK = "200";
	private final Integer NUM_TOTAL_EDITORIALES = 5;

	@Before
	public void setUp() throws Exception {
	     this.base = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO);
	     this.baseById = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_BY_ID);
	     this.baseNuevaEditorial = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_NUEVO);
	     this.baseEliminarEditorial = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_ELIMINAR);
	}

	@Test
	public void getListEditoriales() throws Exception {


	     ResponseEntity<Object[]> response = template.getForEntity(base.toString(), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(NUM_TOTAL_EDITORIALES.intValue(), objects.length);

	 }

	@Test
	public void getEditorialById() throws Exception {


	     ResponseEntity<EditorialDTO> response = template.getForEntity(baseById.toString(), EditorialDTO.class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     EditorialDTO editorial = (EditorialDTO) response.getBody();

	     assertEquals(1, editorial.getId());

	 }

	@Test
	public void nuevaEditorial() throws Exception {
		Integer total = getTotal();

		Gson gson = new Gson();

		EditorialDTO editorial = EditorialDTO.builder().id(6)
						.nombre("Autor 6")
						.build();
		String json = gson.toJson(editorial);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json,headers);

	     ResponseEntity<EditorialDTO> response = template.postForEntity(baseNuevaEditorial.toString(),entity,
	    		 EditorialDTO.class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     assertEquals(6,response.getBody().getId());

	     testSelectAll(total + 1);

	 }

	@Test
	public void eliminarEditorialById() throws Exception {

		Integer total = getTotal();

	     template.delete(baseEliminarEditorial.toString());


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
