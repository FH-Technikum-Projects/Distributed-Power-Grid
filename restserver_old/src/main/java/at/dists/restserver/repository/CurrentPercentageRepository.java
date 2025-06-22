package at.dists.restserver.repository;

import at.dists.restserver.entity.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, Long> {
    Optional<CurrentPercentage> findByHour(LocalDateTime hour);
    List<CurrentPercentage> findByHourBetween(LocalDateTime start, LocalDateTime end);
}