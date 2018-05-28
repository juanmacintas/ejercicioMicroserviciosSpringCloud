package es.ejercicio.microservicios.screen.categorias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;

import es.ejercicio.microservicios.dto.CategoriaDTO;
import es.ejercicio.microservicios.rest.RestClient;
import es.ejercicio.microservicios.screen.AbstractBibliotecaScreen;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = CategoriasScreen.VIEW_NAME)
public class CategoriasScreen extends AbstractBibliotecaScreen implements View {

	public static final String VIEW_NAME = "categorias";

	private static final long serialVersionUID = 2851204137357460687L;

	private Grid<CategoriaDTO> gridCategoria;
	private List<CategoriaDTO> listaCategorias;

	@Autowired
	RestClient restClient;


	@Override
	public void initScreen() {
		log.debug("Inicio Editoriales");
		listaCategorias = restClient.obtenerCategoriasBiblioteca();
		gridCategoria = initLibrosGrid();

		this.addComponents(gridCategoria);

	}

	private Grid<CategoriaDTO> initLibrosGrid() {

		gridCategoria = new Grid<CategoriaDTO>();

		gridCategoria.addColumn(CategoriaDTO::getId).setCaption("Id");
		gridCategoria.addColumn(CategoriaDTO::getNombre).setCaption("Nombre");

		gridCategoria.setEnabled(true);
		gridCategoria.setSelectionMode(SelectionMode.SINGLE);
		gridCategoria.setSizeFull();

		gridCategoria.setItems(this.listaCategorias);

		return gridCategoria;

	}
}
