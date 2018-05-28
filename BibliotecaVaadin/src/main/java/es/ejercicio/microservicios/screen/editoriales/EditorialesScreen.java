package es.ejercicio.microservicios.screen.editoriales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;

import es.ejercicio.microservicios.dto.EditorialDTO;
import es.ejercicio.microservicios.rest.RestClient;
import es.ejercicio.microservicios.screen.AbstractBibliotecaScreen;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = EditorialesScreen.VIEW_NAME)
public class EditorialesScreen extends AbstractBibliotecaScreen implements View {

	public static final String VIEW_NAME = "editoriales";

	private static final long serialVersionUID = 2851204137357460687L;

	private Grid<EditorialDTO> gridEditorial;
	private List<EditorialDTO> listaEditorial;

	@Autowired
	RestClient restClient;


	@Override
	public void initScreen() {
		log.debug("Inicio Editoriales");
		listaEditorial = restClient.obtenerEditorialesBiblioteca();
		gridEditorial = initLibrosGrid();

		this.addComponents(gridEditorial);

	}

	private Grid<EditorialDTO> initLibrosGrid() {

		gridEditorial = new Grid<EditorialDTO>();

		gridEditorial.addColumn(EditorialDTO::getId).setCaption("Id");
		gridEditorial.addColumn(EditorialDTO::getNombre).setCaption("Nombre");

		gridEditorial.setEnabled(true);
		gridEditorial.setSelectionMode(SelectionMode.SINGLE);
		gridEditorial.setSizeFull();

		gridEditorial.setItems(this.listaEditorial);

		return gridEditorial;

	}
}
