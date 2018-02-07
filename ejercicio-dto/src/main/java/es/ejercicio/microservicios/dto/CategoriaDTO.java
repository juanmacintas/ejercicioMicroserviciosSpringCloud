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
public class CategoriaDTO {

    /**
     * Id.
     */
    private int id;

    /**
     * Nombre de la categoria.
     */
    private String nombre;


}
