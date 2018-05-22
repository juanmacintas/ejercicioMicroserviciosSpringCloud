# ejercicioMircroserviciosSpringCloud
Microservices demo using Spring Boot, Spring Cloud Config, Eureka, Feign, Hystrix and Zipkin.

# Ejercicio Microservicios Spring Cloud

* Contiene 10 microservicios distintos.
  * Servidor Configuración (Spring Cloud Config) Microservicio encargado de proveer los ficheros de configuración al resto de microservicios.
  * Servidor Registro (Eureka) Servicio donde se registra el resto de microservicios. Su url de acceso es http://localhost:8084/
  * Servidor Autorización (Oauth2 Authorization Server). Servidor que valida los accesos a los microservicios. Contiene una base de datos 
    con los credenciales permitidos.
  * Servidor Hystrix - (Hystrix Dashboard) - Servicio de información de las distintas ejecuciones de llamadas entre microservicios.
    Su url de acceso es http://localhost:8072/hystrix
  * Servidor Zipkin - (Zipkin Dahsboard) - Servicio de Log distribuido. Permite visualizar la ejecución y llamadas entre los microservicios.
    Su url de acceso es http://localhost:9411
  * Libros - Microservicio mantenimiento entidad libros. 
  * Categorias - Microservicio mantenimiento entidad categorias.
  * Editoriales - Microservicio mantenimiento entidad editoriales.
  * Autores - Microservicio mantenimiento entidad autores.
  * Biblioteca - Microservicio biblioteca.
 
* Contiene 2 librerías de utilidades.
  * ejercicio-dto - Contiene todos los contratos de entrada/salida de los distintos REST usados. Todos los microservicios de la biblioteca 
  lo importan.
  * BibliotecaCliente - Interfaz Feing y Controlador de error con Hystrix implementado para las distintas llamadas a los microservicios. 
  Este cliente es usado por Biblioteca para obtener la información de las disintas entidades.
 
  
  
    
    
  
    


