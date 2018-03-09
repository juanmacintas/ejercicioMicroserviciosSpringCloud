package es.ejercicio.microservicios.biblioteca.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "ejercicio.oauth2.client")
public class Oauth2ClientProperties {

    private String id;
    private String accessTokenUrl;
    private String clientId;
    private String clientSecret;
    private String clientAuthenticationScheme;

}
