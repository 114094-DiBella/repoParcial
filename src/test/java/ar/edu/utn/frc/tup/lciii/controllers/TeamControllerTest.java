package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.services.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TeamControllerTest {

    @MockBean
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTeamsById(){

        Long teamId = 1L;
        TeamResponse teamResponse = new TeamResponse();
        teamResponse.setId(teamId);
        teamResponse.setName("Les Bleus");
        teamResponse.setCountry("Francia");


        when(teamService.getTeamById(teamId)).thenReturn(teamResponse);


        ResponseEntity<TeamResponse> response = teamController.getTeamById(teamId);

        verify(teamService, times(1)).getTeamById(teamId);

        assertEquals(teamResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }


    @Test
    public void testGetAllTeams(){

        TeamResponse[] teams = {
                new TeamResponse(1L, "Les Bleus", "Francia","1", "Pool 1",0,0,0,0),
                new TeamResponse(2L, "Les Rouges", "Francia","2", "Pool 2",0,0,0,0),
                new TeamResponse(3L, "Les Verts", "Francia","3", "Pool 3",0,0,0,0)
        };

        when(teamService.getTeams()).thenReturn(teams);

        ResponseEntity<TeamResponse[]> response = teamController.getTeams();

        verify(teamService, times(1)).getTeams();

        assertEquals(teams, response.getBody(),"The two arrays should be equal");
        assertEquals(200, response.getStatusCodeValue(),"The status code should be 200");
    }


}
