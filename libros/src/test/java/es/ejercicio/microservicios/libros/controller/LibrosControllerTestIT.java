package es.ejercicio.microservicios.libros.controller;

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
import es.ejercicio.microservicios.dto.LibroDTO;
import es.ejercicio.microservicios.dto.OAuthDTO;


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
	private URL baseToken;

	@Autowired
	private TestRestTemplate template;

	private final String NOMBRE_SERVICIO = "libros/getAll/";
	private final String NOMBRE_SERVICIO_FAVORITOS = "libros/getFavoritos/";
	private final String NOMBRE_SERVICIO_BY_EXAMPLE = "libros/getByExample/";
	private final String NOMBRE_SERVICIO_TOKEN= "/uaa/oauth/token";

	private final String STATUS_OK = "200";
	private final Integer NUM_TOTAL_LIBROS = 24;
	private final Integer NUM_TOTAL_FAVORITOS = 5;

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
	     this.baseFavoritos = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_FAVORITOS);
	     this.baseByExample = new URL("http://localhost:" + port + "/"+ NOMBRE_SERVICIO_BY_EXAMPLE);
	     this.baseToken = new URL("http://localhost:" + PORT_AUTHORIZATION_SERVER + "/"+ NOMBRE_SERVICIO_TOKEN);
	     //Se obtiene un token para las pruebas
	     token = getAccessToken();
	}

	@Test
	public void getListLibros() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));

	     ResponseEntity<Object[]> response = template.exchange(base.toString(), HttpMethod.GET,
															new HttpEntity<>(headers), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(NUM_TOTAL_LIBROS.intValue(), objects.length);

	 }

	@Test
	public void getListLibrosFavoritos() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));

		ResponseEntity<Object[]> response = template.exchange(baseFavoritos.toString(), HttpMethod.GET,
																new HttpEntity<>(headers), Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(NUM_TOTAL_FAVORITOS.intValue(), objects.length);

	 }

	@Test
	public void getListLibrosByExample() throws Exception {

		Gson gson = new Gson();

		LibroDTO categoriaDTO = LibroDTO.builder()
						.autor(13)
						.categoria(6)
						.build();
		String json = gson.toJson(categoriaDTO);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AUTHORIZATION_HEADER,BEARER.concat(token));

		HttpEntity<String> entity = new HttpEntity<String>(json,headers);

		 ResponseEntity<Object[]> response = template.postForEntity(baseByExample.toString(),entity,
				 Object[].class);

	     assertEquals(STATUS_OK, response.getStatusCode().toString());
	     Object[] objects = response.getBody();
	     assertEquals(3, objects.length);


	 }

	private String getAccessToken() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    HttpEntity<String> request = new HttpEntity<String>(headers);

	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseToken.toString())
                .queryParam(CLIENT_ID, "librosApp")
                .queryParam(CLIENT_SECRET, "secretLibros")
                .queryParam(GRANT_TYPE, "client_credentials");

	    URI myUri=builder.buildAndExpand().toUri();

	    ResponseEntity<OAuthDTO> response = template.postForEntity(myUri, request, OAuthDTO.class);

	    OAuthDTO autenticacion = (OAuthDTO) response.getBody();


	 return autenticacion.getAccess_token();
	}

}
