package de.govhackathon.wvsvcoronatracker.utils;

import de.ghwct.service.model.UserDto;
import de.govhackathon.wvsvcoronatracker.model.Contact;
import de.govhackathon.wvsvcoronatracker.model.HealthDataSet;
import de.govhackathon.wvsvcoronatracker.model.User;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class TestDataHelper {

    public static User createTestUser() {
        return User.builder()
                .contactDetails(
                        Contact.builder()
                                .phoneHash(UUID.randomUUID().toString())
                                .build())
                .token(UUID.randomUUID().toString())
                .build();
    }

    public static User createTestUser(Set<HealthDataSet> healthDataSetList, Set<Contact> friends) {
        return User.builder()
                .contactDetails(
                        Contact.builder()
                                .phoneHash(UUID.randomUUID().toString())
                                .build())
                .healthDataSetList(healthDataSetList)
                .friends(friends)
                .token(UUID.randomUUID().toString())
                .build();
    }

    public static User createTestUser(Set<Contact> friends) {
        return User.builder()
                .contactDetails(
                        Contact.builder()
                                .phoneHash(UUID.randomUUID().toString())
                                .build())
                .friends(friends)
                .token(UUID.randomUUID().toString())
                .build();
    }

    public static UserDto createTestUserDto() {
        return new UserDto()
                .phoneHash(UUID.randomUUID().toString())
                .healthHistory(Collections.emptyList())
                .token(UUID.randomUUID().toString());
    }
}
