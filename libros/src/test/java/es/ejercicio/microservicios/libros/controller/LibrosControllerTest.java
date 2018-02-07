package es.ejercicio.microservicios.libros.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.ejercicio.microservicios.libros.LibrosApplication;
import es.ejercicio.microservicios.libros.entity.Libro;
import es.ejercicio.microservicios.libros.repository.LibroRepository;
import es.ejercicio.microservicios.libros.service.LibroService;

/**
 * @author Juan Manuel Cintas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibrosApplication.class)
@WebAppConfiguration
public class LibrosControllerTest {
	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	@Autowired
    private WebApplicationContext webApplicationContext;

	@MockBean
    private LibroRepository libroRepository;

	@MockBean
	LibroService libroService;


	@Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


	@Test
	public void testGetAllLibros() {


		List<Libro> listLibros = getLibrosTest();

		when(libroRepository.findAll()).thenReturn(listLibros);
		when(libroService.findAll()).thenReturn(listLibros);
		try {
			ResultActions res = this.mockMvc.perform(get("/libros/getAll"))
									.andDo(print())
						        	.andExpect(status().isOk())
						        	.andExpect(jsonPath("$", not(empty())))
						        	.andExpect(jsonPath("$.[0].id", is(1) ))
									.andExpect(jsonPath("$.[1].id", is(2) ));
		} catch (JsonProcessingException e1) {
			fail(e1.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testGetAllLibrosFavoritos() {


		List<Libro> listLibros = getLibrosTest();

		when(libroRepository.findByFavoriteTrue()).thenReturn(listLibros);
		when(libroService.findByFavoriteTrue()).thenReturn(listLibros);
		try {
			ResultActions res = this.mockMvc.perform(get("/libros/getFavoritos"))
									.andDo(print())
						        	.andExpect(status().isOk())
						        	.andExpect(jsonPath("$", not(empty())))
						        	.andExpect(jsonPath("$.[0].id", is(1) ));
		} catch (JsonProcessingException e1) {
			fail(e1.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	private List<Libro> getLibrosTest() {
		Libro autor1 = Libro.builder().id(1)
				  .autor(1)
				  .categoria(1)
				  .descripcion("Libro 1")
				  .editorial(1)
				  .favorite(true)
				  .build();
		Libro autor2 = Libro.builder().id(2)
				.autor(1)
				.categoria(2)
				.descripcion("Libro 2")
				.editorial(1)
				.favorite(true)
				.build();

		List<Libro> listLibros = new ArrayList<Libro>();
		listLibros.add(autor1);
		listLibros.add(autor2);
		return listLibros;
	}

}
