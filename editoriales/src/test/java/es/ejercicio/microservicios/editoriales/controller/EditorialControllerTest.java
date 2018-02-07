package es.ejercicio.microservicios.editoriales.controller;

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

import es.ejercicio.microservicios.editoriales.EditorialesApplication;
import es.ejercicio.microservicios.editoriales.entity.Editorial;
import es.ejercicio.microservicios.editoriales.repository.EditorialRepository;
import es.ejercicio.microservicios.editoriales.service.EditorialService;

/**
 * @author Juan Manuel Cintas
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EditorialesApplication.class)
@WebAppConfiguration
public class EditorialControllerTest {
	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	@Autowired
    private WebApplicationContext webApplicationContext;

	@MockBean
    private EditorialRepository editorialRepository;

	@MockBean
	EditorialService editorialService;

	@Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


	@Test
	public void testGetAllEditoriales() {

		Editorial editorial1 = Editorial.builder().id(1).nombre("Editorial 1").build();
		Editorial editorial2 = Editorial.builder().id(2).nombre("Editorial 2").build();

		List<Editorial> listEditoriales = new ArrayList<Editorial>();
		listEditoriales.add(editorial1);
		listEditoriales.add(editorial2);


		when(editorialRepository.findAll()).thenReturn(listEditoriales);
		when(editorialService.findAll()).thenReturn(listEditoriales);
		try {
			ResultActions res = this.mockMvc.perform(get("/editoriales/getAll"))
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

		Editorial editorial = Editorial.builder().id(1).nombre("Editorial 1").build();



		when(editorialRepository.findOne(anyInt())).thenReturn(editorial);
		when(editorialService.findById(anyInt())).thenReturn(editorial);
		try {
			ResultActions res = this.mockMvc.perform(get("/editoriales/getEditorial/1"))
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
