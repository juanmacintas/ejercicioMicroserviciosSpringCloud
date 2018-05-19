package es.ejercicio.microservicios.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OAuth2FeignAutoConfiguration {

  private final OAuth2RestTemplate oAuth2RestTemplate;

	private static final String AUTHORIZATION_HEADER = "Authorization";

	private static final String BEARER_TOKEN = "Bearer";

  @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
       // return new OAuth2FeignRequestInterceptor(oAuth2RestTemplate);
	  return  requestTemplate -> {
			final OAuth2AccessToken accessToken = this.oAuth2RestTemplate.getAccessToken();
			final String accessTokenValue = accessToken.getValue();
			log.info("Using access token {}.", accessTokenValue);
			log.info("Request {}.", requestTemplate.toString());
			requestTemplate.header(AUTHORIZATION_HEADER,
	                String.format("%s %s",
	                		BEARER_TOKEN,
	                        oAuth2RestTemplate.getAccessToken().toString()));
		};
    }
}
