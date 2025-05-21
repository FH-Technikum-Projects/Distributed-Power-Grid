package at.dists.restserver2.dto;

import java.time.LocalDateTime;

public class CurrentPercentageDto {
    private LocalDateTime hour;
    private Double communityDepleted;
    private Double gridPortion;

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public LocalDateTime getHour() {
        return hour;
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
