package es.ejercicio.microservicios.screen.libros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;

import es.ejercicio.microservicios.dto.LibroBibliotecaDTO;
import es.ejercicio.microservicios.rest.RestClient;
import es.ejercicio.microservicios.screen.AbstractBibliotecaScreen;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = LibrosFavoritosScreen.VIEW_NAME)
public class LibrosFavoritosScreen extends AbstractBibliotecaScreen implements View {

	public static final String VIEW_NAME = "librosfavoritos";

	private static final long serialVersionUID = 2851204137357460687L;

	private Grid<LibroBibliotecaDTO> gridLibros;
	private List<LibroBibliotecaDTO> listaLibros;

	@Autowired
	RestClient restClient;


	@Override
	public void initScreen() {
		log.debug("Inicio Biblioteca Libros");
		listaLibros = restClient.obtenerLibrosFavoritosBiblioteca();
		gridLibros = initLibrosGrid();

		this.addComponents(gridLibros);

	}

	private Grid<LibroBibliotecaDTO> initLibrosGrid() {

		gridLibros = new Grid<LibroBibliotecaDTO>();

		gridLibros.addColumn(LibroBibliotecaDTO::getId).setCaption("Id");
		gridLibros.addColumn(LibroBibliotecaDTO::getTitulo).setCaption("Título");
		gridLibros.addColumn(LibroBibliotecaDTO::getDescripcion).setCaption("Descripción");
		gridLibros.addColumn(LibroBibliotecaDTO::getAutor).setCaption("Autor");
		gridLibros.addColumn(LibroBibliotecaDTO::getCategoria).setCaption("Categoria");
		gridLibros.addColumn(LibroBibliotecaDTO::getEditorial).setCaption("Editorial");


		gridLibros.setEnabled(true);
		gridLibros.setSelectionMode(SelectionMode.SINGLE);
		gridLibros.setSizeFull();

		gridLibros.setItems(this.listaLibros);

		return gridLibros;

	}
}
