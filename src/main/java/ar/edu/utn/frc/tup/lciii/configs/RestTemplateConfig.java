package ar.edu.utn.frc.tup.lciii.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
/**
 * Clase de configuración para crear y configurar un bean de {@link RestTemplate}.
 * {@link RestTemplate} es una clase de utilidad de Spring para realizar solicitudes HTTP
 * y trabajar con servicios RESTful.
 */
@Configuration
public class RestTemplateConfig {
    // Constante que define el tiempo de espera para conexiones y operaciones de
    // lectura (en milisegundos).

    private static final int TIMEOUT = 5000;
    /**
     * Crea un bean de {@link RestTemplate} con configuraciones personalizadas de tiempo de espera.
     * <p>
     * El método utiliza un {@link RestTemplateBuilder} para configurar el tiempo de espera
     * de conexión y el tiempo de lectura para las solicitudes HTTP.
     * </p>
     *
     * @param builder El {@link RestTemplateBuilder} utilizado para construir el {@link RestTemplate}.
     * @return Una instancia configurada de {@link RestTemplate} con los tiempos de espera especificados.
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(TIMEOUT))
                .setReadTimeout(Duration.ofMillis(TIMEOUT))
                .build();
    }


}
