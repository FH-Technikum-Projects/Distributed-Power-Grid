package at.dists.restserver2.service;

import at.dists.restserver2.dto.CurrentPercentageDto;
import at.dists.restserver2.dto.UsageDto;
import at.dists.restserver2.model.CurrentPercentage;
import at.dists.restserver2.model.Usage;
import at.dists.restserver2.repository.CurrentPercentageRepository;
import at.dists.restserver2.repository.UsageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnergyService {

    private final CurrentPercentageRepository currentPercentageRepo;
    private final UsageRepository usageRepo;

    public EnergyService(CurrentPercentageRepository cp, UsageRepository ur) {
        this.currentPercentageRepo = cp;
        this.usageRepo = ur;
    }

    public CurrentPercentageDto getCurrentPercentage() {
        return currentPercentageRepo.findFirstByOrderByHourDesc()
                .map(this::toDto)
                .orElse(null);
    }

    public List<UsageDto> getHistoricalUsage(LocalDateTime start, LocalDateTime end) {
        return usageRepo.findByHourBetween(start, end)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private CurrentPercentageDto toDto(CurrentPercentage cp) {
        CurrentPercentageDto dto = new CurrentPercentageDto();
        dto.setHour(cp.getHour());
        dto.setCommunityDepleted(cp.getCommunityDepleted());
        dto.setGridPortion(cp.getGridPortion());
        return dto;
    }

    private UsageDto toDto(Usage usage) {
        UsageDto dto = new UsageDto();
        dto.setHour(usage.getHour());
        dto.setCommunityProduced(usage.getCommunityProduced());
        dto.setCommunityUsed(usage.getCommunityUsed());
        dto.setGridUsed(usage.getGridUsed());
        return dto;
    }
}

