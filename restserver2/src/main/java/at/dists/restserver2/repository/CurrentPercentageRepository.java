package at.dists.restserver2.repository;

import at.dists.restserver2.model.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, Long> {
    Optional<CurrentPercentage> findFirstByOrderByHourDesc();
}

