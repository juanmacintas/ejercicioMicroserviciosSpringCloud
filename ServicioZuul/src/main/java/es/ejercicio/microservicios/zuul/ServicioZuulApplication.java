package es.ejercicio.microservicios.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableDiscoveryClient
@EnableZuulProxy
@EnableOAuth2Sso
@EnableResourceServer
@SpringBootApplication
public class ServicioZuulApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServicioZuulApplication.class, args);
    }
}
