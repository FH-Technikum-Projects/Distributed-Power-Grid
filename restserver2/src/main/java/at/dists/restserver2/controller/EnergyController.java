package at.dists.restserver2.controller;

import at.dists.restserver2.dto.CurrentPercentageDto;
import at.dists.restserver2.dto.UsageDto;
import at.dists.restserver2.service.EnergyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    private final EnergyService service;

    public EnergyController(EnergyService service) {
        this.service = service;
    }

    @GetMapping("/current")
    public CurrentPercentageDto getCurrent() {
        return service.getCurrentPercentage();
    }

    @GetMapping("/historical")
    public List<UsageDto> getHistorical(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return service.getHistoricalUsage(start, end);
    }
}

