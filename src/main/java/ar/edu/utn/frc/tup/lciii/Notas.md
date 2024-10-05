
# Notas para Levantar Docker del Proyecto

## 1. Preparar tu Proyecto
- Asegúrate de que tu proyecto esté listo y que hayas construido el archivo `.jar` correspondiente. Este archivo debe estar en la carpeta `target` de tu proyecto, ya que tu Dockerfile está configurado para copiarlo desde ahí.

## 2. Construir la Imagen Docker
- Abre tu terminal y navega al directorio de tu proyecto donde se encuentra tu `Dockerfile`.
- Ejecuta el siguiente comando para construir la imagen Docker:

  ```bash
  docker build -t parcial1-rwc-2023 .

    docker run -d --name parcial1 -p 8080:8080 parcial1-rwc-2023

    docker ps

version: '3'


services:
elecciones-micro:
build: ./parcial-2-elecciones-2023-
image: elecciones-app
ports:
- "8084:8084"
environment:
APP_NAME: elecciones-app


eleccionesApiExterna:
image: tupfrcutn/elecciones-2023:1.0.0
ports:
- "8080:8080"

api-gateway:
image: sha256:6bc4940bec31d311b67ee8d5fadfe3d79f8bfae25e4c5306a426d8632ce258e1
ports:
- "8081:8081"


  ```
application.properties :


spring.mvc.cors.allowed-origins=*
spring.mvc.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.mvc.cors.allowed-headers=*

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
  };
}
}