package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
public class TeamServiceImplTest {

    @Autowired
    private TeamService teamService;



    @Test
    void getTeams() {
        TeamResponse[] teams = teamService.getTeams();
        assertNotNull(teams);
    }

    @Test
    void getTeamById() {
        TeamResponse team = teamService.getTeamById(1L);
        assertNotNull(team);
        assertEquals(team.getId(), 1L, "Team id should be 1");
    }
}
