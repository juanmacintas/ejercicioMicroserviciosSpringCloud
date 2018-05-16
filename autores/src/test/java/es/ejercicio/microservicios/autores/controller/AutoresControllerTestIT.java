package es.ejercicio.microservicios.autores.controller;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
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
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import es.ejercicio.microservicios.dto.AutorDTO;
import es.ejercicio.microservicios.dto.OAuthDTO;


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
	private URL baseToken;

	@Autowired
	private TestRestTemplate template;

	private final String NOMBRE_SERVICIO = "autores/getAll/";
	private final String NOMBRE_SERVICIO_BY_ID= "autores/getAutor/1";
	private final String NOMBRE_SERVICIO_NUEVO= "autores/nuevoAutor";
	private final String NOMBRE_SERVICIO_ELIMINAR= "autores/deleteAutor/2";
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
	     this.baseNuevoAutor = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_NUEVO);
	     this.baseEliminarAutor = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_ELIMINAR);
	     this.baseToken = new URL("http://localhost:" + PORT_AUTHORIZATION_SERVER + "/"+ NOMBRE_SERVICIO_TOKEN);
	     //Se obtiene un token para las pruebas
	     token = getAccessToken();
	}

	@Test
	public void getAutorById() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));


		ResponseEntity<AutorDTO> response = template.exchange(baseById.toString(), HttpMethod.GET,
											new HttpEntity<>(headers), AutorDTO.class);

	    assertEquals(STATUS_OK, response.getStatusCode().toString());
	    AutorDTO autor = (AutorDTO) response.getBody();

	    assertEquals(1, autor.getId());

	 }


	@Test
	public void nuevoAutor() throws Exception {
		Integer total = getTotal();

		Gson gson = new Gson();

		AutorDTO autor = AutorDTO.builder().id(30)
						.nombre("Autor 30")
						.build();
		String json = gson.toJson(autor);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));


		HttpEntity<String> entity = new HttpEntity<String>(json,headers);


	     ResponseEntity<AutorDTO> response = template.postForEntity(baseNuevoAutor.toString(),entity,
	    		 AutorDTO.class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     assertEquals(30,response.getBody().getId());

	     testSelectAll(total + 1);

	 }

	@Test
	public void eliminarAutorById() throws Exception {

		Integer total = getTotal();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));

		ResponseEntity<String> status = template.exchange(baseEliminarAutor.toString(), HttpMethod.DELETE,
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

	    ResponseEntity<Object[]> response = template.exchange(base.toString(), HttpMethod.GET, new HttpEntity<>(headers), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     return objects.length;
	}

	private String getAccessToken() {
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);

		    HttpEntity<String> request = new HttpEntity<String>(headers);

		    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseToken.toString())
                    .queryParam(CLIENT_ID, "autoresApp")
                    .queryParam(CLIENT_SECRET, "secretAutores")
                    .queryParam(GRANT_TYPE, "client_credentials");

		    URI myUri=builder.buildAndExpand().toUri();

		    ResponseEntity<OAuthDTO> response = template.postForEntity(myUri, request, OAuthDTO.class);

		    OAuthDTO autenticacion = (OAuthDTO) response.getBody();

		 return autenticacion.getAccess_token();
	}

}
