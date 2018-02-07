package es.ejercicio.microservicios.categorias.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.google.gson.Gson;

import es.ejercicio.microservicios.categorias.CategoriasApplication;
import es.ejercicio.microservicios.categorias.entity.Categoria;
import es.ejercicio.microservicios.categorias.repository.CategoriaRepository;
import es.ejercicio.microservicios.categorias.service.CategoriaService;
import es.ejercicio.microservicios.dto.CategoriaDTO;

/**
 * @author Juan Manuel Cintas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CategoriasApplication.class)
@WebAppConfiguration
public class CategoriaControllerTest {
	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	@Autowired
    private WebApplicationContext webApplicationContext;

	@MockBean
    private CategoriaRepository categoriaRepository;

	@MockBean
	CategoriaService categoriaService;

	@Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


	@Test
	public void testGetAllCategorias() {

		Categoria categoria1 = Categoria.builder().id(1).nombre("Categoria 1").build();
		Categoria categoria2 = Categoria.builder().id(2).nombre("Categoria 2").build();

		List<Categoria> listCategorias = new ArrayList<Categoria>();
		listCategorias.add(categoria1);
		listCategorias.add(categoria2);


		when(categoriaRepository.findAll()).thenReturn(listCategorias);
		when(categoriaService.findAll()).thenReturn(listCategorias);
		try {
			ResultActions res = this.mockMvc.perform(get("/categorias/getAll"))
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
	public void testCategoriaPorId() {

		Categoria categoria = Categoria.builder().id(1).nombre("Categoria 1").build();

		when(categoriaRepository.findOne(anyInt())).thenReturn(categoria);
		when(categoriaService.findById(anyInt())).thenReturn(categoria);
		try {
			ResultActions res = this.mockMvc.perform(get("/categorias/getCategoria/1"))
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

	@Test
	public void testNuevaCategoria() {

		Categoria categoria = Categoria.builder().id(1).nombre("Categoria 1").build();

		CategoriaDTO categoriaDTO = CategoriaDTO.builder().id(1).build();

		Gson gson = new Gson();


	    String json = gson.toJson(categoriaDTO);


		when(categoriaRepository.save(categoria)).thenReturn(categoria);
		when(categoriaService.nuevaCategoria(anyObject())).thenReturn(categoria);
		try {
			ResultActions res = this.mockMvc.perform(post("/categorias/nuevaCategoria/")
									.contentType(MediaType.APPLICATION_JSON).content(json))
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
