package at.dists.restserver.repository;

import at.dists.restserver.dto.CurrentPercentageDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PercentageRepository extends CrudRepository<CurrentPercentageDto, Long> {

    @Query("SELECT p FROM CurrentPercentageDto p WHERE p.hour = :hour")
    CurrentPercentageDto findByHour(LocalDateTime hour);

    @Query("SELECT p FROM CurrentPercentageDto p WHERE p.hour BETWEEN :start AND :end")
    List<CurrentPercentageDto> findByHourBetween(LocalDateTime start, LocalDateTime end);
}
