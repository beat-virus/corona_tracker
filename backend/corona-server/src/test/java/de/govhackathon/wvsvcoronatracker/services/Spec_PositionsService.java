package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.Position;
import de.govhackathon.wvsvcoronatracker.repositories.PositionsRepository;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = Spec_PositionsService.Initializer.class)
@Testcontainers
public class Spec_PositionsService {

    @Autowired
    private PositionsService service;

    @Autowired
    private PositionsRepository repository;

    @BeforeEach
    public void before(){
        repository.deleteAll();
    }


    @ClassRule
    public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgis/postgis:11-3.0");

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgres.start();
            TestPropertyValues.of("spring.datasource.url="+postgres.getJdbcUrl(),
                    "spring.datasource.username="+postgres.getUsername(),"spring.datasource.password="+postgres.getPassword()).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    // FFM : 50.124660, 8.676400 (true)
    // Mainz: 49.988510, 8.241459 (false)
    // Punkt oben links: 50.170564, 8.590553
    // Punkt unten rechts: 50.076398, 8.764088
    @Test
    public void testGetPositionsByTimestampBetweenAndPositionInRectangle(){
        OffsetDateTime now = OffsetDateTime.now();
        Position ffm = new Position();
        ffm.setTimestamp(now);
        ffm.setLocation(service.createPoint(50.124660, 8.676400));
        Position mainz = new Position();
        mainz.setTimestamp(now);
        mainz.setLocation(service.createPoint(49.988510, 8.241459));

        ffm = service.savePosition(ffm);
        mainz = service.savePosition(mainz);
        List<Position> actual = service.getPositionsByTimestampBetweenAndInRectangle(now.minusMinutes(30),now.plusMinutes(30),50.170564, 8.590553,50.076398, 8.764088);
        Assertions.assertThat(actual).contains(ffm).doesNotContain(mainz);
    }



    @Test
    public void testGetPositionsByTimestamp60MinutesAndPositionInRectangle(){
        OffsetDateTime now = OffsetDateTime.now();
        Position ffm = new Position();
        ffm.setTimestamp(now.plusMinutes(61));
        ffm.setLocation(service.createPoint(50.124660, 8.676400));
        Position ffm2 = new Position();
        ffm2.setTimestamp(now.plusMinutes(1));
        ffm2.setLocation(service.createPoint(50.11, 8.6));

        ffm = service.savePosition(ffm);
        ffm2 = service.savePosition(ffm2);
        List<Position> actual = service.getCurrentPositionByTimestampNotOlderThanMinutesAndInRectangle(60,50.170564, 8.590553,50.076398, 8.764088);
        Assertions.assertThat(actual).contains(ffm2).doesNotContain(ffm);
    }
}
