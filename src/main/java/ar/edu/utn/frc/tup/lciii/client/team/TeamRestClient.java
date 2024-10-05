package ar.edu.utn.frc.tup.lciii.client.team;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeamRestClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String RESILIENCE4J_INSTANCE_NAME = "postCircuitBreaker";
    private static final String FALLBACK_METHOD = "fallback";
    private static final Map<Long, TeamResponse> TEAM_CACHE = new HashMap<>();
    private static final String URL_BASE = "https://my-json-server.typicode.com/LCIV-2023/fake-api-rwc2023";

    /**
     * Obtiene todos los equipos con manejo de circuito de Resilience4j.
     */
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<TeamResponse[]> getTeams() {
        try {
            return restTemplate.getForEntity(URL_BASE + "/teams", TeamResponse[].class);
        } catch (RestClientException e) {
            // Manejo de error de cliente Rest (fallo de red, etc.)
            System.err.println("Error al obtener los equipos: " + e.getMessage());
            throw new RuntimeException("Error al conectarse con el servicio externo de equipos.", e);
        }
    }

    /**
     * Obtiene un equipo por su ID, usando caché local si es posible.
     */
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<TeamResponse> getTeamById(Long id) {
        // Validación de ID: No puede ser nulo o menor o igual a 0
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del equipo no puede ser nulo o menor o igual a 0.");
        }

        try {
            // Si el equipo no está en caché, realiza una solicitud externa
            if (!TEAM_CACHE.containsKey(id)) {
                TeamResponse team = restTemplate.getForEntity(URL_BASE + "/teams/" + id, TeamResponse.class).getBody();

                // Validación: Verifica si la respuesta es válida
                if (team == null) {
                    throw new RuntimeException("El equipo con ID " + id + " no fue encontrado.");
                }

                TEAM_CACHE.put(id, team);
            }

            return ResponseEntity.ok(TEAM_CACHE.get(id));
        } catch (RestClientException e) {
            // Manejo de errores de red o cliente HTTP
            System.err.println("Error al obtener el equipo con ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Error al conectarse con el servicio externo de equipos.", e);
        }
    }

    /**
     * Método fallback en caso de error. Devuelve una respuesta genérica o vacía.
     */
    public ResponseEntity<MatchResponse> fallback(Exception e) {
        // Aquí puedes definir lo que sucederá si ocurre un error en las llamadas.
        System.err.println("Circuito abierto o error al obtener datos. Fallback activado: " + e.getMessage());
        return ResponseEntity.status(503).build(); // Devuelve un estado 503 (Servicio no disponible)
    }
}
