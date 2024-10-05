package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.services.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;


    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamResponse> getTeamById(Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }


    @GetMapping("/teams")
    public ResponseEntity<TeamResponse[]> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }
}
