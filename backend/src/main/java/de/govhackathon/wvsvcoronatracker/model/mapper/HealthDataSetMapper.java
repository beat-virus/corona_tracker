package de.govhackathon.wvsvcoronatracker.model.mapper;

import de.ghwct.service.model.HealthDataSetDto;
import de.govhackathon.wvsvcoronatracker.model.HealthDataSet;
import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class HealthDataSetMapper {
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "creationDate", source = "time")
    @Mapping(target = "user", expression = "java(getUser(dto.getUserId()))")
    abstract public HealthDataSet toEntity(HealthDataSetDto dto);

    @InheritInverseConfiguration
    @Mapping(target = "userId", source = "user.token")
    abstract public HealthDataSetDto toDto(HealthDataSet entity);

    public User getUser(String userId) {
        Optional<User> existingUser = this.userRepository.findById(userId);
        return existingUser.isPresent() ? existingUser.get() : null;
    }
}
