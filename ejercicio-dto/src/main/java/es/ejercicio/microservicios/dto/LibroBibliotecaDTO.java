package es.ejercicio.microservicios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Juan Manuel Cintas
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibroBibliotecaDTO {

    /**
     * Id.
     */
    private int id;

    /**
     * Titulo del libro.
     */
    private String titulo;

    /**
     * Descripcion.
     */
    private String descripcion;

    /**
     * Categoria
     */
    private String categoria;

    /**
     * Autor
     */
    private String autor;

    /**
     * Editorial.
     */
    private String editorial;

}
