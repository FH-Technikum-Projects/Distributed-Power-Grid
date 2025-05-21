package at.dists.restserver2.repository;

import at.dists.restserver2.model.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsageRepository extends JpaRepository<Usage, Long> {
    List<Usage> findByHourBetween(LocalDateTime start, LocalDateTime end);
}

