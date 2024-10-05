package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import ar.edu.utn.frc.tup.lciii.client.match.MatchRestClient;
import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.models.Match;
import ar.edu.utn.frc.tup.lciii.models.Team;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRestClient matchRestClient;

    @Override
    public MatchResponse[] getMatches() {
       return matchRestClient.getMatches();
    }


    @Override
    public MatchResponse getMatchById(Long id) {
        return matchRestClient.getMatchById(id);
    }

    @Override
    public MatchResponse[] getMatchesByPool(String pool) {
        return matchRestClient.getMatchesByPool(pool);
    }

    private Match convertToMatch(MatchResponse matchResponse) {
        Match match = new Match();
        match.setId(matchResponse.getId());
        match.setDate(matchResponse.getDate());
        match.setStadium(matchResponse.getStadium());
        match.setPool(matchResponse.getPool());
        match.setTeams(matchResponse.getTeams().stream().map(this::convertToTeam).collect(Collectors.toList()));
        // Otros mapeos que sean necesarios
        return match;
    }

    private Team convertToTeam(TeamResponse teamResponse) {
        Team team = new Team();
        team.setId(teamResponse.getId());
        team.setName(teamResponse.getName());
        team.setCountry(teamResponse.getCountry());
        team.setWorldRanking(teamResponse.getWorldRanking());
        team.setPool(teamResponse.getPool());
        // Otros mapeos que sean necesarios
        return team;
    }
}
