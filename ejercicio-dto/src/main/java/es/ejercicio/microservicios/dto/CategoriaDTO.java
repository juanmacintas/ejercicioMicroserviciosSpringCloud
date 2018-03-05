package es.ejercicio.microservicios.dto;

import io.swagger.annotations.ApiModelProperty;
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

	@ApiModelProperty(notes = "ID de la Categoría. Clave primaria", required = true)
    private int id;

	@ApiModelProperty(notes = "Nombre de la Categoría")
    private String nombre;


}
