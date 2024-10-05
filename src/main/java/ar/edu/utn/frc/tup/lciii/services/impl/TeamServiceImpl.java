package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.client.team.TeamRestClient;
import ar.edu.utn.frc.tup.lciii.services.TeamService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRestClient teamRestClient;
    @Override
    public TeamResponse getTeamById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del equipo no puede ser nulo o menor a 1.");
        }
        ResponseEntity<TeamResponse> response = teamRestClient.getTeamById(id);
        if (response == null || response.getBody() == null) {
            throw new IllegalArgumentException("No se encontró ningún equipo con el ID: " + id);
        }

        return response.getBody();
    }

    @Override
    public TeamResponse[] getTeams() {
        ResponseEntity<TeamResponse[]> response = teamRestClient.getTeams();

        // Validación: Verifica si la respuesta es nula o no contiene equipos
        if (response == null || ObjectUtils.isEmpty(response.getBody())) {
            throw new IllegalArgumentException("No se encontraron equipos.");
        }

        return response.getBody();
    }
}
