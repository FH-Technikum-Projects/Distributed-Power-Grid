package at.dists.restserver.service;

import at.dists.restserver.dto.CurrentPercentageDto;
import at.dists.restserver.mapper.CurrentPercentageMapper;
import at.dists.restserver.entity.CurrentPercentage;
import at.dists.restserver.repository.CurrentPercentageRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrentPercentageService {

    private final CurrentPercentageRepository repository;

    public CurrentPercentageService(CurrentPercentageRepository repository) {
        this.repository = repository;
    }

    public CurrentPercentageDto getLatestPercentage() {
        return repository.findAll().stream()
                .reduce((first, second) -> second) // letzter Eintrag
                .map(CurrentPercentageMapper::toDto)
                .orElse(null);
    }

    public void save(CurrentPercentageDto dto) {
        repository.save(CurrentPercentageMapper.toEntity(dto));
    }
}
