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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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

	@Autowired
	private TestRestTemplate template;

	private final String NOMBRE_SERVICIO = "editoriales/getAll/";
	private final String NOMBRE_SERVICIO_BY_ID= "editoriales/getEditorial/1";

	private final String STATUS_OK = "200";
	private final Integer NUM_TOTAL_EDITORIALES = 5;

	@Before
	public void setUp() throws Exception {
	     this.base = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO);
	     this.baseById = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_BY_ID);
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

}
