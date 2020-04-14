package de.govhackathon.wvsvcoronatracker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ghwct.service.model.FriendDto;
import de.ghwct.service.model.UserDto;
import de.govhackathon.wvsvcoronatracker.model.HealthDataSet;
import de.govhackathon.wvsvcoronatracker.model.MedicalState;
import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.repositories.HealthDataSetRepository;
import de.govhackathon.wvsvcoronatracker.repositories.PositionsRepository;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.govhackathon.wvsvcoronatracker.utils.TestDataHelper.createTestUser;
import static de.govhackathon.wvsvcoronatracker.utils.TestDataHelper.createTestUserDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
class Spec_UsersController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionsRepository positionsRepository;

    @Autowired
    private HealthDataSetRepository healthDataSetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class User_Management {

        @BeforeEach
        void setUp() {
            positionsRepository.deleteAll();
            userRepository.deleteAll();
            healthDataSetRepository.deleteAll();
        }

        @Test
        void should_add_new_user() throws Exception {
            UserDto dto = createTestUserDto();

            String content = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isOk());
        }

        @Test
        void should_read_saved_user() throws Exception {
            UserDto dto = createTestUserDto();
            String content = objectMapper.writeValueAsString(dto);

            ResultActions result = mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isOk());
            result.andDo(mvcResult -> {
                UserDto savedItem = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);
                Assertions.assertThat(savedItem.getToken()).isNotNull();
                mockMvc.perform(get("/api/v1/users/" + savedItem.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isOk());
            });
        }

        @Test
        void should_delete_healthdata_with_user_delete() throws Exception {
            User user = userRepository.save(createTestUser());
            healthDataSetRepository.save(HealthDataSet.builder()
                    .medicalState(MedicalState.INFECTED)
                    .user(user)
                    .build());
            healthDataSetRepository.save(HealthDataSet.builder()
                    .medicalState(MedicalState.INFECTED)
                    .user(user)
                    .build());
            ResultActions result = mockMvc.perform(delete("/api/v1/users/" + user.getToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
            result.andDo(mvcResult -> {
                Iterable<HealthDataSet> dataSets = healthDataSetRepository.findAll();
                Assertions.assertThat(dataSets.iterator().hasNext()).isFalse();
            });
        }

        // error handling


        @Test
        void should_not_save_invalid_user() throws Exception {
            UserDto dto = new UserDto()
                    .token("123");

            String content = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isBadRequest());
        }
    }


    @Nested
    @SpringBootTest
    @AutoConfigureMockMvc(addFilters = false)
    @TestPropertySource(locations = "classpath:application-test.properties")
    @ExtendWith(SpringExtension.class)
    @WithMockUser(username = "Test", roles = "APP_USER")
    class Friends {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private PositionsRepository positionsRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
            positionsRepository.deleteAll();
        }

        @Test
        void should_add_friend() throws Exception {
            UserDto user = createTestUserDto();
            this.mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk()).andDo(mvcResult -> {
                UserDto savedItem = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);
                Assertions.assertThat(savedItem.getToken()).isNotNull();
                FriendDto friend = new FriendDto().phoneHash(UUID.randomUUID().toString());
                this.mockMvc.perform(post("/api/v1/users/" + savedItem.getToken() + "/friends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(friend)))
                        .andExpect(status().isOk());
            });
        }

        @Test
        void should_upload_friends() throws Exception {
            UserDto user = createTestUserDto();
            this.mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk()).andDo(mvcResult -> {
                UserDto savedItem = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);
                Assertions.assertThat(savedItem.getToken()).isNotNull();
                FriendDto friend1 = new FriendDto().phoneHash(UUID.randomUUID().toString());
                List<FriendDto> friends = new ArrayList<>();
                friends.add(friend1);
                this.mockMvc.perform(put("/api/v1/users/" + savedItem.getToken() + "/friends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(friends)))
                        .andExpect(status().isOk());
            });
        }
    }
}