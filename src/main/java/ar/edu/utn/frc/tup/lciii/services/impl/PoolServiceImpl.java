package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.dtos.common.PoolDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.TeamDto;
import ar.edu.utn.frc.tup.lciii.models.Team;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import ar.edu.utn.frc.tup.lciii.services.PoolService;
import ar.edu.utn.frc.tup.lciii.services.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PoolServiceImpl implements PoolService {

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PoolDto getPool(String pool) {
        MatchResponse[] matches = matchService.getMatchesByPool(pool); //
        return buildPoolDto(matches);
    }

    private PoolDto buildPoolDto(MatchResponse[] matches) {

        if(matches == null || matches.length == 0){
            return new PoolDto();
        }
        PoolDto poolDto = new PoolDto();
        TeamResponse[] teams = teamService.getTeams();
        List<TeamDto> teamDtoList = Arrays.stream(teams)
                .map(teamResponse -> modelMapper.map(teamResponse, TeamDto.class))
                .collect(Collectors.toList());
        poolDto.setTeams(teamDtoList);

        return poolDto;
    }

    @Override
    public List<PoolDto> getAllPools() {
        return null;
    }
}
