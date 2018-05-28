package es.ejercicio.microservicios.screen.autores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;

import es.ejercicio.microservicios.dto.AutorDTO;
import es.ejercicio.microservicios.rest.RestClient;
import es.ejercicio.microservicios.screen.AbstractBibliotecaScreen;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = AutoresScreen.VIEW_NAME)
public class AutoresScreen extends AbstractBibliotecaScreen implements View {

	public static final String VIEW_NAME = "autores";

	private static final long serialVersionUID = 2851204137357460687L;

	private Grid<AutorDTO> gridAutores;
	private List<AutorDTO> listaAutores;

	@Autowired
	RestClient restClient;


	@Override
	public void initScreen() {
		log.debug("Inicio Autores");
		listaAutores = restClient.obtenerAutoresBiblioteca();
		gridAutores = initLibrosGrid();

		this.addComponents(gridAutores);

	}

	private Grid<AutorDTO> initLibrosGrid() {

		gridAutores = new Grid<AutorDTO>();

		gridAutores.addColumn(AutorDTO::getId).setCaption("Id");
		gridAutores.addColumn(AutorDTO::getNombre).setCaption("Nombre");

		gridAutores.setEnabled(true);
		gridAutores.setSelectionMode(SelectionMode.SINGLE);
		gridAutores.setSizeFull();

		gridAutores.setItems(this.listaAutores);

		return gridAutores;

	}
}
