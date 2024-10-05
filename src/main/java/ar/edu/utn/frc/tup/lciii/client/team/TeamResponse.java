package ar.edu.utn.frc.tup.lciii.client.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponse {

    private Long id;
    private String name;
    private String country;
    @JsonProperty("world_ranking")
    private String worldRanking;
    private String pool;

    private int points;
    private int tries;
    private int yellow_cards;
    private int red_cards;


    public TeamResponse(String teamA) {
        name = teamA;
    }
}
