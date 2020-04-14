package de.govhackathon.wvsvcoronatracker.model.mapper;

import de.ghwct.service.model.PositionDto;
import de.govhackathon.wvsvcoronatracker.model.Position;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;


@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PositionMapper {

    Position toEntity(PositionDto dto);

    @InheritInverseConfiguration
    PositionDto toDto(Position entity);
}
