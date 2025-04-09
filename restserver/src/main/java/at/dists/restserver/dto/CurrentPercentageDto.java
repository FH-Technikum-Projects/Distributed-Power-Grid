package at.dists.restserver.dto;

import java.time.LocalDateTime;

public class CurrentPercentageDto {
    private LocalDateTime hour;
    private Double communityDepleted;
    private Double gridPortion;

    public CurrentPercentageDto() {
    }

    public CurrentPercentageDto(LocalDateTime hour, Double communityDepleted, Double gridPortion) {
        this.hour = hour;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public Double getCommunityDepleted() {
        return communityDepleted;
    }

    public void setCommunityDepleted(Double communityDepleted) {
        this.communityDepleted = communityDepleted;
    }

    public Double getGridPortion() {
        return gridPortion;
    }

    public void setGridPortion(Double gridPortion) {
        this.gridPortion = gridPortion;
    }
}
