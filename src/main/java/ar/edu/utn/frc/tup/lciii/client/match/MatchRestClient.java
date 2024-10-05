
package ar.edu.utn.frc.tup.lciii.client.match;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

    /**
     * Cliente REST para consumir los servicios relacionados con partidos (matches) de una API externa.
     * Implementa un Circuit Breaker utilizando Resilience4J para manejar fallos en las llamadas a los servicios.
     */
    @Service
    public class MatchRestClient {

        @Autowired
        private RestTemplate restTemplate;

        private static final String RESILIENCE4J_INSTANCE_NAME = "postCircuitBreaker";
        private static final String FALLBACK_METHOD = "fallback";
        private static final String urlBase = "https://my-json-server.typicode.com/LCIV-2023/fake-api-rwc2023";

        /**
         * Obtiene una lista de partidos desde la API externa.
         * En caso de fallo, Resilience4J activará el Circuit Breaker y redirigirá a la función de fallback.
         *
         * @return ResponseEntity con un arreglo de MatchResponse, que contiene la lista de partidos.
         */
        @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
        public MatchResponse[] getMatches() {
            try {
                MatchResponse[] result = restTemplate.getForObject(urlBase + "/matches", MatchResponse[].class);
                if (result != null) {
                    return result;
                }
            } catch (Exception e) {
                System.out.println("Error al obtener Matches" + e);
            }
            return null;
        }


        /**
         * Obtiene un partido específico por su ID desde la API externa.
         * Si el partido ya está en la caché, se devuelve el resultado desde la misma.
         * En caso de fallo, se activa el Circuit Breaker y se llama al método de fallback.
         *
         * @param id El ID del partido que se desea obtener.
         * @return ResponseEntity con la información del partido (MatchResponse) correspondiente al ID solicitado.
         */
        @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
        public MatchResponse getMatchById(Long id) {
            return restTemplate.getForObject(urlBase + "/matches/" + id, MatchResponse.class);
        }

        @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
        public MatchResponse[] getMatchesByPool(String poolId) {
            return restTemplate.getForObject(urlBase + "/matches?pool=" + poolId, MatchResponse[].class);
        }


        /**
         * Método de fallback que se ejecuta cuando el Circuit Breaker detecta fallos en la llamada a la API externa.
         * Devuelve una respuesta HTTP con estado 503 (Servicio no disponible).
         *
         * @param e La excepción que causó la activación del Circuit Breaker.
         * @return ResponseEntity con un estado de error 503 (Servicio no disponible).
         */
        public ResponseEntity<Match> fallback(Exception e){
            System.out.println("Error al obtener partidos: " + e.getMessage());
            return ResponseEntity.status(503).build();
        }
    }
