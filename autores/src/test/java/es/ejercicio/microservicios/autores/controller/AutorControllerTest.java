package es.ejercicio.microservicios.autores.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
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

import es.ejercicio.microservicios.autores.AutoresApplication;
import es.ejercicio.microservicios.autores.entity.Autor;
import es.ejercicio.microservicios.autores.repository.AutorRepository;
import es.ejercicio.microservicios.autores.service.AutorService;

/**
 * @author Juan Manuel Cintas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoresApplication.class)
@WebAppConfiguration
public class AutorControllerTest {
	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	@Autowired
    private WebApplicationContext webApplicationContext;

	@MockBean
    private AutorRepository autorRepository;

	@MockBean
	AutorService autorService;

	@Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


	@Test
	public void testGetAllAutores() {

		Autor autor1 = Autor.builder().id(1).nombre("Autor 1").build();
		Autor autor2 = Autor.builder().id(2).nombre("Autor 2").build();

		List<Autor> listAutores = new ArrayList<Autor>();
		listAutores.add(autor1);
		listAutores.add(autor2);


		when(autorRepository.findAll()).thenReturn(listAutores);
		when(autorService.findAll()).thenReturn(listAutores);
		try {
			ResultActions res = this.mockMvc.perform(get("/autores/getAll"))
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


	@Test
	public void testAutorPorId() {

		Autor autor1 = Autor.builder().id(1).nombre("Autor 1").build();



		when(autorRepository.findOne(anyInt())).thenReturn(autor1);
		when(autorService.findById(anyInt())).thenReturn(autor1);
		try {
			ResultActions res = this.mockMvc.perform(get("/autores/getAutor/1"))
									.andDo(print())
						        	.andExpect(status().isOk())
						        	.andExpect(jsonPath("$", not(empty())))
						        	.andExpect(jsonPath("$.id", is(1) ));
		} catch (JsonProcessingException e1) {
			fail(e1.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
