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
public class AutorDTO {

    /**
     * Id.
     */
    private int id;

    /**
     * Nombre del autor.
     */
    private String nombre;


}
