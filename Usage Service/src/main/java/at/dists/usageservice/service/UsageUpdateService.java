package at.dists.usageservice.service;

import at.dists.usageservice.model.UsageData;
import at.dists.usageservice.repository.UsageDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class UsageUpdateService {

    private final UsageDataRepository repository;

    public UsageUpdateService(UsageDataRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void updateUsage(String type, double kwh, LocalDateTime timestamp) {
        LocalDateTime hour = timestamp.truncatedTo(ChronoUnit.HOURS);
        UsageData data = repository.findByHour(hour).orElseGet(() -> {
            UsageData newData = new UsageData();
            newData.setHour(hour);
            return newData;
        });

        switch (type.toUpperCase()) {
            case "PRODUCER" -> data.setCommunityProduced(data.getCommunityProduced() + kwh);
            case "USER" -> {
                double available = data.getCommunityProduced() - data.getCommunityUsed();
                if (available >= kwh) {
                    data.setCommunityUsed(data.getCommunityUsed() + kwh);
                } else {
                    data.setCommunityUsed(data.getCommunityUsed() + available);
                    data.setGridUsed(data.getGridUsed() + (kwh - available));
                }
            }
        }

        repository.save(data);
    }
}
