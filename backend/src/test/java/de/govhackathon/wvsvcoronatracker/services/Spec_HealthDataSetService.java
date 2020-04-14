package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.HealthDataSet;
import de.govhackathon.wvsvcoronatracker.model.MedicalState;
import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.repositories.HealthDataSetRepository;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static de.govhackathon.wvsvcoronatracker.utils.TestDataHelper.createTestUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class Spec_HealthDataSetService {

    @Autowired
    private HealthDataSetService service;

    @Autowired
    private HealthDataSetRepository repository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        userRepository.deleteAll();
        testUser = userRepository.save(createTestUser());
    }

    @Nested
    class Medical_State {
        @Test
        void should_add_state() throws Exception {
            HealthDataSet dataSet = HealthDataSet.builder()
                    .user(testUser)
                    .medicalState(MedicalState.UNKNOWN)
                    .build();
            HealthDataSet savedDataSet = service.addHealthDataSet(dataSet);
            Optional<User> user = userRepository.findById(testUser.getToken());
            assertThat(user.get()).isNotNull();
            assertThat(savedDataSet.getId()).isNotNull();
            assertThat(user.get().getHealthDataSetList()).isNotNull();
        }
    }
}