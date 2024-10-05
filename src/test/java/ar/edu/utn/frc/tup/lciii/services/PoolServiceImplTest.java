package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.dtos.common.PoolDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.TeamDto;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import ar.edu.utn.frc.tup.lciii.services.TeamService;
import ar.edu.utn.frc.tup.lciii.services.impl.PoolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PoolServiceImplTest {

    @MockBean
    private PoolService poolService;

    @MockBean
    private MatchService matchService;

    @MockBean(name = "modelMapper")
    private ModelMapper modelMapper;
    @MockBean
    private TeamService teamService;

    @InjectMocks
    private PoolServiceImpl poolServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testGetPools() throws ParseException {

        String pool = "Pool 1";
        var matchDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01");

        TeamResponse[] teams = {
                new TeamResponse(1L, "Les Bleus", "Francia", "1", "Pool 1",0,0,0,0),
                new TeamResponse(2L, "Les Rouges", "Francia", "2", "Pool 1",0,0,0,0)
        };
        List<TeamResponse> teamList = List.of(teams);
        MatchResponse[] matchResponse = {
                new MatchResponse(1L, matchDate,teamList, "Stadium 1", pool)
        };

        when(matchService.getMatchesByPool(pool)).thenReturn(matchResponse);
        when(teamService.getTeams()).thenReturn(teams);

        PoolDto poolDto = poolServiceImpl.getPool(pool);

        // Usar TypeToken para mapear la lista correctamente
        poolDto.setTeams(modelMapper.map(teams, new TypeToken<List<TeamResponse>>() {}.getType()));

        verify(matchService, times(1)).getMatchesByPool(pool);
        verify(teamService, times(1)).getTeams();

        assertNotNull(poolDto);

    }



}
