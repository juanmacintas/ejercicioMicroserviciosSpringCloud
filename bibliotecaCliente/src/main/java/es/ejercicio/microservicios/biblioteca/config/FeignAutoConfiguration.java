package es.ejercicio.microservicios.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FeignAutoConfiguration {


  @Bean
  public RequestInterceptor feignRequestInterceptor() {

	  return  new RequestInterceptor() {

		@Override
		public void apply(RequestTemplate arg0) {
			log.debug("Llamada a :"  + arg0.toString());

		}
	};



    }
}
