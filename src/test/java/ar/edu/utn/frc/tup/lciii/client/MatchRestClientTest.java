package ar.edu.utn.frc.tup.lciii.client;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import ar.edu.utn.frc.tup.lciii.client.match.MatchRestClient;
import ar.edu.utn.frc.tup.lciii.models.Match;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MatchRestClientTest {

    @InjectMocks
    private MatchRestClient matchRestClient;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMatchByIdFallback() {
        Long matchId = 1L;

        // Simular un error al llamar a la API
        when(restTemplate.getForEntity(anyString(), eq(MatchResponse.class)))
                .thenThrow(new RuntimeException("Error de conexión"));

        // Llamar al método que debería activar el fallback
        MatchResponse response = matchRestClient.getMatchById(matchId);

        // Verificar que el código de estado sea 503
        assertEquals(null, response);
    }

    @Test
    public void testGetMatchesFallback() {
        when(restTemplate.getForObject(anyString(), eq(MatchResponse[].class)))
                .thenThrow(new RuntimeException("Error de conexión"))
                .thenThrow(new RuntimeException("Error de conexión"));

        // Hacer suficientes llamadas para activar el Circuit Breaker
        for (int i = 0; i < 5; i++) {
            matchRestClient.getMatches();
        }

        // Verificar que el método de fallback se activó
        MatchResponse[] result = matchRestClient.getMatches();

        // El resultado debería ser el valor devuelto por el fallback
        assertEquals(null, result);}

    @Disabled
    @Test
    public void testFallbackDirectly() {
        // Simulamos la llamada directa al método de fallback
        ResponseEntity<DataFormatReaders.Match> response = matchRestClient.fallback(new RuntimeException("Test Exception"));

        // Verificamos que el estado devuelto sea 503
        assertEquals(503, response.getStatusCodeValue());
    }

}
