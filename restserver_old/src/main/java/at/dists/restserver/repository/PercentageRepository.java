package at.dists.restserver.repository;

import at.dists.restserver.entity.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PercentageRepository extends JpaRepository<CurrentPercentage, LocalDateTime> {
    Optional<CurrentPercentage> findByHour(LocalDateTime hour);
}
