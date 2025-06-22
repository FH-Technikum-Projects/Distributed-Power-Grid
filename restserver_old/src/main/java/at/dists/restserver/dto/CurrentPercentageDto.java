package at.dists.restserver.dto;

import java.time.LocalDateTime;

public class CurrentPercentageDto {

    private LocalDateTime hour;
    private double communityDepleted;
    private double gridPortion;

    public CurrentPercentageDto() {}

    public CurrentPercentageDto(LocalDateTime hour, double communityDepleted, double gridPortion) {
        this.hour = hour;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }

    // Getter & Setter
    public LocalDateTime getHour() { return hour; }
    public void setHour(LocalDateTime hour) { this.hour = hour; }

    public double getCommunityDepleted() { return communityDepleted; }
    public void setCommunityDepleted(double communityDepleted) { this.communityDepleted = communityDepleted; }

    public double getGridPortion() { return gridPortion; }
    public void setGridPortion(double gridPortion) { this.gridPortion = gridPortion; }
}
