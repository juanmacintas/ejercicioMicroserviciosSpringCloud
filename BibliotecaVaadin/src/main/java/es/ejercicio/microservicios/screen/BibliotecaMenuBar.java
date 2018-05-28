package es.ejercicio.microservicios.screen;

import com.vaadin.ui.MenuBar;

import es.ejercicio.microservicios.screen.autores.AutoresScreen;
import es.ejercicio.microservicios.screen.categorias.CategoriasScreen;
import es.ejercicio.microservicios.screen.editoriales.EditorialesScreen;
import es.ejercicio.microservicios.screen.libros.LibrosFavoritosScreen;
import es.ejercicio.microservicios.screen.libros.LibrosScreen;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BibliotecaMenuBar extends MenuBar {

	private static final long serialVersionUID = 4570934148969606674L;

	private MenuItem itemLibros;
	private MenuItem itemLibrosFavoritos;
	private MenuItem itemLibrosTodos;
	private MenuItem itemAutores;
	private MenuItem itemListadoAutores;
	private MenuItem itemCategorias;
	private MenuItem itemListadoCategorias;
	private MenuItem itemEditoriales;
	private MenuItem itemListadoEditoriales;
	private MenuItem itemSalir;

	public BibliotecaMenuBar() {
		super();

		itemLibros = this.addItem("Libros", null, null);
		itemLibrosTodos = itemLibros.addItem("Libros", null,  selectedItem -> menuSelected(selectedItem));
		itemLibrosFavoritos = itemLibros.addItem("Libros Favoritos", null,  selectedItem -> menuSelected(selectedItem));
		itemAutores = this.addItem("Autores", null, null);
		itemListadoAutores = itemAutores.addItem("Listado Autores", null,  selectedItem -> menuSelected(selectedItem));
		itemCategorias = this.addItem("Categorias", null, null);
		itemListadoCategorias = itemCategorias.addItem("Listado Categorias", null,  selectedItem -> menuSelected(selectedItem));
		itemEditoriales = this.addItem("Editoriales", null, null);
		itemListadoEditoriales = itemEditoriales.addItem("Listado Editoriales", null,  selectedItem -> menuSelected(selectedItem));
		itemSalir = this.addItem("Salir", null, selectedItem -> menuSelected(selectedItem));

	}

	private void menuSelected(MenuItem selectedItem) {
		log.info("Selected item: " + selectedItem.getId());

		final int itemLibrosId = itemLibrosTodos != null ? itemLibrosTodos.getId() : -1;
		final int itemLibrosFavId = itemLibrosFavoritos != null ? itemLibrosFavoritos.getId() : -1;
		final int itemListadoAutoresId = itemListadoAutores != null ? itemListadoAutores.getId() : -1;
		final int itemListadoCategoriasId = itemListadoCategorias != null ? itemListadoCategorias.getId() : -1;
		final int itemListadoEditorialesId = itemListadoEditoriales != null ? itemListadoEditoriales.getId() : -1;
		final int itemSalirId= itemSalir != null ? itemSalir.getId() : -1;


		if (selectedItem != null && selectedItem.getId() != -1) {

			if (itemLibrosId == selectedItem.getId()) {
				getUI().getNavigator().navigateTo(LibrosScreen.VIEW_NAME);
			} else if (itemLibrosFavId == selectedItem.getId()) {
				getUI().getNavigator().navigateTo(LibrosFavoritosScreen.VIEW_NAME);
		    } else if (itemListadoAutoresId == selectedItem.getId()) {
				getUI().getNavigator().navigateTo(AutoresScreen.VIEW_NAME);
			} else if (itemListadoEditorialesId == selectedItem.getId()) {
				getUI().getNavigator().navigateTo(EditorialesScreen.VIEW_NAME);
		    } else if (itemListadoCategoriasId == selectedItem.getId()) {
				getUI().getNavigator().navigateTo(CategoriasScreen.VIEW_NAME);
		    } else if (itemSalirId == selectedItem.getId()) {
				getUI().getNavigator().navigateTo(IndexScreen.VIEW_NAME);
			}
		}


	}

}
