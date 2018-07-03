# ejercicioMircroserviciosSpringCloud
Microservices demo using Spring Boot, Spring Cloud Config, Eureka, Feign, Hystrix and Zipkin.

# Ejercicio Microservicios Spring Cloud

* Contiene 12 microservicios distintos.
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
  * Biblioteca - Microservicio biblioteca.(Microservicio Edge que accede al resto de microservicios)
  * ServicioZuul - Proxy Inverso con entrada a la aplicación. Tiene parametrizado el microservicio Biblioteca.
  * BibliotecaVaadin - Frontal Web Vaadin para visualizar la información. Hace uso del servidio Edge de Bilioteca para el acceso a datos. Su url de acceso es http://localhost:8086/
 
* Contiene 2 librerías de utilidades.
  * ejercicio-dto - Contiene todos los contratos de entrada/salida de los distintos REST usados. Todos los microservicios de la biblioteca 
  lo importan.
  * BibliotecaCliente - Interfaz Feing y Controlador de error con Hystrix implementado para las distintas llamadas a los microservicios. 
  Este cliente es usado por Biblioteca para obtener la información de las disintas entidades.

* La entrada para obtener la información será a traves del puerto 8080 de Zuul.
  * Para obtener todos los libros     -> http://localhost:8080/biblioteca/biblioteca/getAll  
  * Para obtener los libros favoritos -> http://localhost:8080/biblioteca/biblioteca/getAllFavoritos
  * Para obtener un libro por su id   -> http://localhost:8080/biblioteca/biblioteca/getLibro/1

   


# Actualización Docker
* Para generar imagen docker
  * mvn package -P docker 
  * mvn -DCONFIG_HOST=192.168.99.100 package -P docker 



Para establecer valor de variable de entorno al iniciar contenedor incluir  -e VARIABLE = VALOR
* Para arrancar servicio configuracion:
  docker run -p 8888:8888 -d --name servidorconfiguracion servidorconfiguracion:0.0.1-SNAPSHOT
* Para arrancar servicio eureka
  docker run -p 8084:8084 -d --name servidorregistro servidorregistro:0.0.1-SNAPSHOT                                                                                             
* Para arrancar servidor zipkin
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -p 9411:9411 -d --name servidorzipkin servidorzipkin:0.0.1-SNAPSHOT  
* Para arrancar servidor autotizacion
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -p 9000:9000 -d --name servidorautorizacion servidorautorizacion:0.0.1-SNAPSHOT 
* Para arrancar servicio zuul
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -p 8080:8080 -d --name serviciozuul serviciozuul:0.0.1-SNAPSHOT  
* Para arrancar autores
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -d --name autores autores:0.0.1-SNAPSHOT
* Para arrancar categorias
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -d --name categorias categorias:0.0.1-SNAPSHOT
* Para arrancar editoriales
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -d --name editoriales editoriales:0.0.1-SNAPSHOT 
* Para arrancar libros
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -d --name libros libros:0.0.1-SNAPSHOT
* Para arrancar biblioteca
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -d --name biblioteca biblioteca:0.0.1-SNAPSHOT
* Para arrancar bibliotecavaadin
  docker run -e CONFIG_HOST=192.168.99.100 -e PROFILE=docker  -p  8086:8086 -d --name bibliotecavaadin bibliotecavaadin:0.0.1-SNAPSHOT       
  
                                            
La url de acceso es http://192.168.99.100:8086/    
    
  
    


