package at.dists.usageservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_data")
public class UsageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime hour;

    @Column(name = "community_produced")
    private Double communityProduced = 0.0;

    @Column(name = "community_used")
    private Double communityUsed = 0.0;

    @Column(name = "grid_used")
    private Double gridUsed = 0.0;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public Double getCommunityProduced() {
        return communityProduced;
    }

    public void setCommunityProduced(Double communityProduced) {
        this.communityProduced = communityProduced;
    }

    public Double getCommunityUsed() {
        return communityUsed;
    }

    public void setCommunityUsed(Double communityUsed) {
        this.communityUsed = communityUsed;
    }

    public Double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(Double gridUsed) {
        this.gridUsed = gridUsed;
    }
}
