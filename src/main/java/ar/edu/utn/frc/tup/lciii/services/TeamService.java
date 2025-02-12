package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.models.Team;
import org.springframework.stereotype.Service;

@Service
public interface TeamService {

    TeamResponse getTeamById(Long id);

    TeamResponse[] getTeams();
}
