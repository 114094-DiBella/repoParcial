package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.client.match.MatchResponse;
import ar.edu.utn.frc.tup.lciii.client.match.MatchRestClient;
import ar.edu.utn.frc.tup.lciii.models.Match;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface MatchService {

    MatchResponse[] getMatches(); // <id, <id, Match>>

    MatchResponse getMatchById(Long id);

    MatchResponse[] getMatchesByPool(String pool);
}
