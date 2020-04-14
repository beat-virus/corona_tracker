package de.govhackathon.wvsvcoronatracker.model.mapper;

import de.govhackathon.wvsvcoronatracker.model.User;
import org.mapstruct.*;

import de.ghwct.service.model.UserDto;


@Mapper(componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = { HealthDataSetMapper.class })
public interface UserMapper {

  @Mappings({
          @Mapping(target = "healthDataSetList", source = "healthHistory"),
          @Mapping(target = "contactDetails.phoneHash", source = "phoneHash")
  })
  User toEntity(UserDto dto);

  @InheritInverseConfiguration
  @Mappings({
          @Mapping(target = "healthHistory", source = "healthDataSetList"),
          @Mapping(target = "phoneHash", source = "contactDetails.phoneHash")
  })
  UserDto toDto(User entity);

}
