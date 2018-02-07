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
public class LibroDTO {

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
     * Categoria ID.
     */
    private Integer categoria;

    /**
     * Autor ID.
     */
    private Integer autor;

    /**
     * Editorial ID.
     */
    private Integer editorial;

    /**
     * Favorite
     */
    private Boolean favorite;

}
