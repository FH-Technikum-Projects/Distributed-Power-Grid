package at.dists.restserver.controller;

import at.dists.restserver.dto.CurrentPercentageDto;
import at.dists.restserver.dto.UsageDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    @GetMapping("/current")
    public CurrentPercentageDto getCurrentPercentage() {
        // Dummy-Daten als Beispiel
        LocalDateTime currentHour = LocalDateTime.of(2025, 1, 10, 14, 0, 0);
        double communityDepleted = 100.0;
        double gridPortion = 5.63;
        return new CurrentPercentageDto(currentHour, communityDepleted, gridPortion);
    }

    /**
     * GET /energy/historical?start=2025-01-10T14:00:00&end=2025-01-10T16:00:00
     * Liefert Test Daten für den angefragten Zeitraum.
     */
    @GetMapping("/historical")
    public List<UsageDto> getHistoricalUsage(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        // Test Daten zurückgegeben.
        List<UsageDto> usageList = new ArrayList<>();

        usageList.add(new UsageDto(LocalDateTime.of(2025, 1, 10, 14, 0), 18.05, 18.05, 1.076));
        usageList.add(new UsageDto(LocalDateTime.of(2025, 1, 10, 15, 0), 15.015, 14.033, 2.049));

        return usageList;
    }
}
