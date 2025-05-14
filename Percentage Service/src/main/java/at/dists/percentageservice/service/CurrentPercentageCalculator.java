package at.dists.percentageservice.service;

import at.dists.percentageservice.model.CurrentPercentage;
import at.dists.percentageservice.repository.CurrentPercentageRepository;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class CurrentPercentageCalculator {

    private final JdbcTemplate jdbcTemplate;
    private final CurrentPercentageRepository repository;

    public CurrentPercentageCalculator(JdbcTemplate jdbcTemplate, CurrentPercentageRepository repository) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
    }

    @Transactional
    public void recalculatePercentage() {
        LocalDateTime hour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        String sql = "SELECT community_used, grid_used FROM usage_data WHERE hour = ?";
        var row = jdbcTemplate.queryForList(sql, hour);

        if (row.isEmpty()) return;

        double communityUsed = ((Number) row.get(0).get("community_used")).doubleValue();
        double gridUsed = ((Number) row.get(0).get("grid_used")).doubleValue();
        double totalUsed = communityUsed + gridUsed;

        double gridPortion = totalUsed > 0 ? (gridUsed / totalUsed) * 100.0 : 0.0;

        CurrentPercentage percentage = repository.findByHour(hour).orElseGet(CurrentPercentage::new);
        percentage.setHour(hour);
        percentage.setCommunityDepleted(100.0); // Annahme laut Aufgabenstellung
        percentage.setGridPortion(Math.round(gridPortion * 100.0) / 100.0); // zwei Nachkommastellen

        repository.save(percentage);
    }
}
