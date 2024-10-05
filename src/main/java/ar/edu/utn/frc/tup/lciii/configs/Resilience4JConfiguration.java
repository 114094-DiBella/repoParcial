package ar.edu.utn.frc.tup.lciii.configs;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Clase de configuración para definir circuit breakers utilizando Resilience4j.
 * Se crean configuraciones globales y específicas para controlar el comportamiento
 * de circuitos con tiempos de espera y manejo de fallas.
 */
@Configuration
public class Resilience4JConfiguration {

    /**
     * Configuración global para todos los circuit breakers que no tengan una configuración específica.
     * <p>
     * La configuración define un umbral del 50% de fallas antes de abrir el circuito,
     * con una espera de 5 segundos en estado abierto y una ventana deslizante de 10 intentos.
     * También limita las operaciones a un tiempo máximo de 2 segundos.
     * </p>
     *
     * @return Customizer para configurar el comportamiento predeterminado de los circuit breakers.
     */
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
        // Configura el Circuit Breaker con un umbral de fallo del 50% y una ventana de 10 intentos.
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)  // Umbral del 50% para considerar fallas.
                .waitDurationInOpenState(Duration.ofSeconds(5))  // Espera de 5 segundos en estado abierto.
                .slidingWindowSize(10)  // Tamaño de la ventana deslizante.
                .build();

        // Configura un límite de tiempo para las operaciones (máximo 2 segundos).
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(2))  // Limita las operaciones a 2 segundos.
                .build();

        // Retorna la configuración aplicada por defecto a todos los circuitos
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }

    /**
     * Configuración específica para un circuito que requiere parámetros más agresivos.
     * <p>
     * Esta configuración aplica un umbral de fallo muy bajo (1%), una espera mínima
     * en estado abierto (1 ms) y una ventana deslizante de solo 2 intentos. Además, limita
     * las operaciones a un tiempo máximo de 1 ms.
     * </p>
     *
     * @return Customizer para configurar un circuito breaker específico con el ID "circuitBreaker".
     */
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> specificCustomConfiguration() {
        // Configuración del Circuit Breaker con un umbral de fallo del 1% y ventana de 2 intentos.
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(1)  // Umbral de fallo del 1%.
                .waitDurationInOpenState(Duration.ofMillis(1))  // Espera mínima de 1 ms en estado abierto.
                .slidingWindowSize(2)  // Ventana deslizante de solo 2 intentos.
                .build();

        // Configura un límite de tiempo muy estricto para las operaciones (1 ms).
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(1))  // Límite de tiempo de 1 ms.
                .build();

        // Aplica la configuración específica para el circuito con ID "circuitBreaker".
        return factory -> factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(timeLimiterConfig).build(), "circuitBreaker");
    }

}
