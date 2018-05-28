package es.ejercicio.microservicios.screen;

import java.util.Iterator;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.ui.HasComponents;
import com.vaadin.ui.VerticalLayout;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public abstract class AbstractBibliotecaScreen extends VerticalLayout {

	private static final long serialVersionUID = -46212438974612614L;

	@PostConstruct
	public void init() {
		log.info("Inicio {}", this.getClass().getName());

		this.removeAllComponents();

		this.addComponent(new BibliotecaMenuBar());

		initScreen();

		log.info("Fin {}", this.getClass().getName());

	}

	public abstract void initScreen();

	protected Optional<com.vaadin.ui.Component> getComponentById(String id,
			Iterator<com.vaadin.ui.Component> itComponent) {

		while (itComponent.hasNext()) {
			com.vaadin.ui.Component component = itComponent.next();
			if (id.equals(component.getId())) {
				return Optional.of(component);
			} else if (component instanceof HasComponents) {
				return getComponentById(id, ((HasComponents) component).iterator());
			}
		}

		return Optional.empty();
	}

}
