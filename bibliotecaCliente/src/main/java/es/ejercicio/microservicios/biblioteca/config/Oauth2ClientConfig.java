package es.ejercicio.microservicios.biblioteca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import es.ejercicio.microservicios.biblioteca.config.properties.Oauth2ClientProperties;

@Configuration
@EnableConfigurationProperties(Oauth2ClientProperties.class)
public class Oauth2ClientConfig {

	@Autowired
	private Oauth2ClientProperties oauth2ClientProperties;

	@Bean(name = "ejercicioClientCredentialsResourceDetails")
    public ClientCredentialsResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setId(oauth2ClientProperties.getId());
        resourceDetails.setAccessTokenUri(oauth2ClientProperties.getAccessTokenUrl());
        resourceDetails.setClientId(oauth2ClientProperties.getClientId());
        resourceDetails.setClientSecret(oauth2ClientProperties.getClientSecret());
        resourceDetails.setAuthenticationScheme(AuthenticationScheme.valueOf(oauth2ClientProperties.getClientAuthenticationScheme()));
        return resourceDetails;
    }

    @Bean(name = "ejercicioOAuth2RestTemplate")
    public OAuth2RestTemplate oAuth2RestTemplate() {
        final OAuth2RestTemplate oAuth2RestTemplate =
        			new OAuth2RestTemplate(resourceDetails(),
        			new DefaultOAuth2ClientContext());
        oAuth2RestTemplate.setRequestFactory(new Netty4ClientHttpRequestFactory());

        return oAuth2RestTemplate;

    }
}
