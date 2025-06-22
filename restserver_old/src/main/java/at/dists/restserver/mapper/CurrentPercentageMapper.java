package at.dists.restserver.mapper;

import at.dists.restserver.dto.CurrentPercentageDto;
import at.dists.restserver.entity.CurrentPercentage;

public class CurrentPercentageMapper {

    public static CurrentPercentageDto toDto(CurrentPercentage entity) {
        return new CurrentPercentageDto(entity.getHour(), entity.getCommunityDepleted(), entity.getGridPortion());
    }

    public static CurrentPercentage toEntity(CurrentPercentageDto dto) {
        return new CurrentPercentage(dto.getHour(), dto.getCommunityDepleted(), dto.getGridPortion());
    }
}
