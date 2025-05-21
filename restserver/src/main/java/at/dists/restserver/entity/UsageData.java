package at.dists.restserver.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_data")
public class UsageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime hour;

    @Column(name = "community_produced")
    private Double communityProduced;

    @Column(name = "community_used")
    private Double communityUsed;

    @Column(name = "grid_used")
    private Double gridUsed;

    // Getter/Setter
    public Long getId() { return id; }
    public LocalDateTime getHour() { return hour; }
    public void setHour(LocalDateTime hour) { this.hour = hour; }
    public Double getCommunityProduced() { return communityProduced; }
    public void setCommunityProduced(Double communityProduced) { this.communityProduced = communityProduced; }
    public Double getCommunityUsed() { return communityUsed; }
    public void setCommunityUsed(Double communityUsed) { this.communityUsed = communityUsed; }
    public Double getGridUsed() { return gridUsed; }
    public void setGridUsed(Double gridUsed) { this.gridUsed = gridUsed; }
}
