package ar.edu.utn.frc.tup.lciii.client.match;

import ar.edu.utn.frc.tup.lciii.client.team.TeamResponse;
import ar.edu.utn.frc.tup.lciii.models.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("teams")
    private List<TeamResponse> teams; // <1>
    @JsonProperty("stadium")
    private String stadium;
    @JsonProperty("pool")
    private String pool;
}
