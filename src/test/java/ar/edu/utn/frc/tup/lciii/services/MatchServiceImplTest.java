package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import ar.edu.utn.frc.tup.lciii.client.match.MatchRestClient;
import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.models.Match;
import ar.edu.utn.frc.tup.lciii.services.impl.MatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MatchServiceImplTest {

    @MockBean
    private MatchRestClient matchRestClient;

    @InjectMocks
    private MatchServiceImpl matchService;

    private MatchServiceImpl matchServicee;



    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        matchServicee = new MatchServiceImpl(/* Dependencias necesarias */);
    }

    @Test
    void testConvertToMatchUsingReflection() throws Exception {
        // Crear el objeto MatchResponse para el test
        var matchDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01");

        MatchResponse matchResponse = new MatchResponse();
        matchResponse.setId(1L);
        matchResponse.setDate(matchDate);
        matchResponse.setStadium("Stadium 1");
        matchResponse.setPool("Pool A");
        matchResponse.setTeams(Arrays.asList(new TeamResponse("Team A"), new TeamResponse("Team B")));

        // Obtener el método privado usando Reflection
        Method method = MatchServiceImpl.class.getDeclaredMethod("convertToMatch", MatchResponse.class);
        method.setAccessible(true);

        // Invocar el método privado
        Match match = (Match) method.invoke(matchService, matchResponse);

        // Verificar los resultados
        assertNotNull(match);
        assertEquals(1L, match.getId());
        assertEquals("Stadium 1", match.getStadium());
        assertEquals("Pool A", match.getPool());
        assertEquals(2, match.getTeams().size());
    }
}
