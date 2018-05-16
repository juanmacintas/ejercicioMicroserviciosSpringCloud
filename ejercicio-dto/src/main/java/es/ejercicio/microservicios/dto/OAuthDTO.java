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
public class OAuthDTO {


    private String access_token;

    private String token_type;

    private Long expires_in;

    private String scope;
}
