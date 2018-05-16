package es.ejercicio.microservicios.editoriales.controller;

import static org.junit.Assert.assertEquals;

import java.net.URI;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import es.ejercicio.microservicios.dto.EditorialDTO;
import es.ejercicio.microservicios.dto.OAuthDTO;


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
	private URL baseToken;

	@Autowired
	private TestRestTemplate template;

	private final String NOMBRE_SERVICIO = "editoriales/getAll/";
	private final String NOMBRE_SERVICIO_BY_ID= "editoriales/getEditorial/1";
	private final String NOMBRE_SERVICIO_NUEVO= "editoriales/nuevaEditorial";
	private final String NOMBRE_SERVICIO_ELIMINAR= "editoriales/deleteEditorial/2";
	private final String NOMBRE_SERVICIO_TOKEN= "/uaa/oauth/token";

	private final String STATUS_OK = "200";
	private final Integer PORT_AUTHORIZATION_SERVER = 9000;

	private String token;

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER = "Bearer ";
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String GRANT_TYPE = "grant_type";

	@Before
	public void setUp() throws Exception {
	     this.base = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO);
	     this.baseById = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_BY_ID);
	     this.baseNuevaEditorial = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_NUEVO);
	     this.baseEliminarEditorial = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_ELIMINAR);
	     this.baseToken = new URL("http://localhost:" + PORT_AUTHORIZATION_SERVER + "/"+ NOMBRE_SERVICIO_TOKEN);
	     //Se obtiene un token para las pruebas
	     token = getAccessToken();
	}

	@Test
	public void getEditorialById() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));

	     ResponseEntity<EditorialDTO> response = template.exchange(baseById.toString(), HttpMethod.GET,
															new HttpEntity<>(headers), EditorialDTO.class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     EditorialDTO editorial = (EditorialDTO) response.getBody();

	     assertEquals(1, editorial.getId());

	 }

	@Test
	public void nuevaEditorial() throws Exception {
		Integer total = getTotal();

		Gson gson = new Gson();

		EditorialDTO editorial = EditorialDTO.builder().id(12)
						.nombre("Autor 12")
						.build();
		String json = gson.toJson(editorial);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));

		HttpEntity<String> entity = new HttpEntity<String>(json,headers);

	     ResponseEntity<EditorialDTO> response = template.postForEntity(baseNuevaEditorial.toString(),entity,
	    		 EditorialDTO.class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     assertEquals(12,response.getBody().getId());

	     testSelectAll(total + 1);

	 }

	@Test
	public void eliminarEditorialById() throws Exception {

		Integer total = getTotal();


	    HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));

		ResponseEntity<String> status = template.exchange(baseEliminarEditorial.toString(), HttpMethod.DELETE,
				new HttpEntity<>(headers),String.class);
		assertEquals(STATUS_OK, status.getStatusCode().toString());

	    testSelectAll(total - 1);

	 }

	private void testSelectAll(Integer totalEsperado) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(AUTHORIZATION_HEADER, BEARER.concat(token));

		ResponseEntity<Object[]> response = template.exchange(base.toString(), HttpMethod.GET,
								new HttpEntity<>(headers), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(totalEsperado.intValue(), objects.length);

	}

	private int getTotal() {
		 HttpHeaders headers = new HttpHeaders();
		 headers.add(AUTHORIZATION_HEADER, BEARER.concat(token));

	     ResponseEntity<Object[]> response = template.exchange(base.toString(), HttpMethod.GET,
	    		 								new HttpEntity<>(headers), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     return objects.length;
	}

	private String getAccessToken() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    HttpEntity<String> request = new HttpEntity<String>(headers);

	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseToken.toString())
                .queryParam(CLIENT_ID, "editorialesApp")
                .queryParam(CLIENT_SECRET, "secretEditoriales")
                .queryParam(GRANT_TYPE, "client_credentials");

	    URI myUri=builder.buildAndExpand().toUri();

	    ResponseEntity<OAuthDTO> response = template.postForEntity(myUri, request, OAuthDTO.class);

	    OAuthDTO autenticacion = (OAuthDTO) response.getBody();


	 return autenticacion.getAccess_token();
}

}
