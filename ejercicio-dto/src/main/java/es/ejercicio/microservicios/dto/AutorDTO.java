package es.ejercicio.microservicios.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel
public class AutorDTO {


	@ApiModelProperty(notes = "ID del Autor. Clave primaria", required = true)
    private int id;

	@ApiModelProperty(notes = "Nombre del Autor")
    private String nombre;


}
