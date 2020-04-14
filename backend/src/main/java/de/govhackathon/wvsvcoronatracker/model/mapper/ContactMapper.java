package de.govhackathon.wvsvcoronatracker.model.mapper;

import de.ghwct.service.model.FriendDto;
import de.govhackathon.wvsvcoronatracker.model.Contact;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;
import java.util.Set;


@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ContactMapper {

    List<FriendDto> toDto(Set<Contact> entities);

    FriendDto toDto(Contact entity);

    Contact toEntity(FriendDto to);

}
