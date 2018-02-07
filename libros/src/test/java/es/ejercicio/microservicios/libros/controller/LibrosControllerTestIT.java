package es.ejercicio.microservicios.libros.controller;

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

import es.ejercicio.microservicios.dto.CategoriaDTO;
import es.ejercicio.microservicios.dto.LibroDTO;


/**
 * @author Juan Manuel Cintas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibrosControllerTestIT {

	@LocalServerPort
	private int port;

	private URL base;
	private URL baseFavoritos;
	private URL baseByExample;

	@Autowired
	private TestRestTemplate template;

	private final String NOMBRE_SERVICIO = "libros/getAll/";
	private final String NOMBRE_SERVICIO_FAVORITOS = "libros/getFavoritos/";
	private final String NOMBRE_SERVICIO_BY_EXAMPLE = "libros/getByExample/";

	private final String STATUS_OK = "200";
	private final Integer NUM_TOTAL_LIBROS = 8;
	private final Integer NUM_TOTAL_FAVORITOS = 2;

	@Before
	public void setUp() throws Exception {
	     this.base = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO);
	     this.baseFavoritos = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_FAVORITOS);
	     this.baseByExample = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_BY_EXAMPLE);
	}

	@Test
	public void getListLibros() throws Exception {


	     ResponseEntity<Object[]> response = template.getForEntity(base.toString(), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(NUM_TOTAL_LIBROS.intValue(), objects.length);

	 }

	@Test
	public void getListLibrosFavoritos() throws Exception {


	     ResponseEntity<Object[]> response = template.getForEntity(baseFavoritos.toString(), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(NUM_TOTAL_FAVORITOS.intValue(), objects.length);

	 }

	@Test
	public void getListLibrosByExample() throws Exception {

		Gson gson = new Gson();

		LibroDTO categoriaDTO = LibroDTO.builder()
						.autor(2)
						.categoria(2)
						.build();
		String json = gson.toJson(categoriaDTO);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json,headers);

		 ResponseEntity<Object[]> response = template.postForEntity(baseByExample.toString(),entity,
				 Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(1, objects.length);


	 }

}
