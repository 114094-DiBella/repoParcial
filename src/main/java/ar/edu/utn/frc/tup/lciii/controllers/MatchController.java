package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import ar.edu.utn.frc.tup.lciii.models.Match;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/matches")
    public ResponseEntity< MatchResponse[]> getMatches() {
       return ResponseEntity.ok(matchService.getMatches());
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<MatchResponse> getMatchById(Long id) {

    return ResponseEntity.ok(matchService.getMatchById(id));

    }

    @GetMapping("/matches/pool/{pool}")
    public ResponseEntity<MatchResponse[]> getMatchesByPool(String pool) {
        return ResponseEntity.ok(matchService.getMatchesByPool(pool));
    }
}
