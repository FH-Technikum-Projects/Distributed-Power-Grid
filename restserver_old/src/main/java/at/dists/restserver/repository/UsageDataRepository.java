package at.dists.restserver.repository;

import at.dists.restserver.entity.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsageDataRepository extends JpaRepository<UsageData, Long> {
    List<UsageData> findByHourBetween(LocalDateTime start, LocalDateTime end);
}
