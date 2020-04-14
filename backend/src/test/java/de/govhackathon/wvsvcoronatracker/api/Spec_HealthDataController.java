package de.govhackathon.wvsvcoronatracker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ghwct.service.model.HealthDataSetDto;
import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.repositories.HealthDataSetRepository;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static de.govhackathon.wvsvcoronatracker.utils.TestDataHelper.createTestUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
class Spec_HealthDataController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HealthDataSetRepository repository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    ;

    @Nested
    class MedicalState {

        @BeforeEach
        void setUp() {
            repository.deleteAll();
            userRepository.deleteAll();
            testUser = userRepository.save(createTestUser());
        }

        @Test
        void should_add_new_record() throws Exception {
            HealthDataSetDto dto = new HealthDataSetDto()
                    .userId(testUser.getToken())
                    .medicalState(HealthDataSetDto.MedicalStateEnum.UNKNOWN);

            mockMvc.perform(post("/api/v1/healthDataSet")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk());
        }

        // error handling


        @Test
        void should_not_save_invalid_data() throws Exception {
            HealthDataSetDto dto = new HealthDataSetDto()
                    .userId(testUser.getToken());

            mockMvc.perform(post("/api/v1/healthDataSet")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }
    }
}