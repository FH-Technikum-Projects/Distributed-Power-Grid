package at.dists.usageservice.repository;

import at.dists.usageservice.model.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsageDataRepository extends JpaRepository<UsageData, Long> {
    Optional<UsageData> findByHour(LocalDateTime hour);
}
