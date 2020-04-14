package de.govhackathon.wvsvcoronatracker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ghwct.service.model.PositionDto;
import de.govhackathon.wvsvcoronatracker.repositories.PositionsRepository;
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
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
class Spec_PositionsController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionsRepository positionsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class Position_Tracking {

        @BeforeEach
        void setUp() {
            positionsRepository.deleteAll();
        }

        @Test
        void should_add_new_position() throws Exception {
            PositionDto dto = new PositionDto()
                    .latitude(new BigDecimal(1.1)).altitude(new BigDecimal(2.1));

            String content = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post("/api/v1/positions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isOk());
        }

        @Test
        void should_read_saved_position() throws Exception {
            PositionDto dto = new PositionDto()
                    .latitude(new BigDecimal(1.1)).altitude(new BigDecimal(2.1));

            String content = objectMapper.writeValueAsString(dto);

            ResultActions result = mockMvc.perform(post("/api/v1/positions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isOk());
            result.andDo(mvcResult -> {
                PositionDto savedItem = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), PositionDto.class);
                assertThat(savedItem.getId()).isNotNull();
                mockMvc.perform(get("/api/v1/positions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isOk());
            });
        }
    }
}