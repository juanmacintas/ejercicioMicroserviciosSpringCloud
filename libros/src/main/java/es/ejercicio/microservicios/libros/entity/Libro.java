package es.ejercicio.microservicios.libros.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Juan Manuel Cintas
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    /**
     * Id.
     */
    @Id
    private Integer id;

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
