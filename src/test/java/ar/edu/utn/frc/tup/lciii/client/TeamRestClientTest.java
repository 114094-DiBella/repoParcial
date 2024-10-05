package ar.edu.utn.frc.tup.lciii.client;

import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.client.team.TeamRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamRestClientTest {

    @Autowired
    private TeamRestClient teamRestClient;


    @Test
    void getAllTeams() {
        ResponseEntity<TeamResponse[]> teams = teamRestClient.getTeams();
        assertEquals(20, Objects.requireNonNull(teams.getBody()).length);
    }

    @Test
    void getTeamById() {
        ResponseEntity<TeamResponse> team = teamRestClient.getTeamById(1L);
        assertEquals("Les Bleus", Objects.requireNonNull(team.getBody()).getName());
    }
}
