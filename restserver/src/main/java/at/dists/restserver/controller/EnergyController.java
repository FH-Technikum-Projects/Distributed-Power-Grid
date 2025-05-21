package at.dists.restserver.controller;

import at.dists.restserver.entity.CurrentPercentage;
import at.dists.restserver.entity.UsageData;
import at.dists.restserver.repository.CurrentPercentageRepository;
import at.dists.restserver.repository.UsageDataRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    private final CurrentPercentageRepository percentageRepo;
    private final UsageDataRepository usageRepo;

    public EnergyController(CurrentPercentageRepository percentageRepo, UsageDataRepository usageRepo) {
        this.percentageRepo = percentageRepo;
        this.usageRepo = usageRepo;
    }

    @GetMapping("/current")
    public CurrentPercentage getCurrentPercentage() {
        LocalDateTime currentHour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        return percentageRepo.findByHour(currentHour).orElse(null);
    }

    @GetMapping("/historical")
    public List<UsageData> getHistoricalUsage(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return usageRepo.findByHourBetween(start, end);
    }
}