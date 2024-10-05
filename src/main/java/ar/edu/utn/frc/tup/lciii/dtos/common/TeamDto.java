package ar.edu.utn.frc.tup.lciii.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private Long id;
    private String name;
    private String country;
    private Integer matchesPlayed;
    private Integer wins;
    private Integer draws;
    private Integer losses;
    private Integer pointsFor;
    private Integer pointsAgainst;
    private Integer pointsDifferential;
    private Integer triesMade;
    private Integer bonusPoints;
    private Integer points;
    private Integer totalYellowCards;
    private Integer totalRedCards;

}
