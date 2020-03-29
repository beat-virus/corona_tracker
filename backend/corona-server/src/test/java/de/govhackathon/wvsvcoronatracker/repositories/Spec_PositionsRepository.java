package de.govhackathon.wvsvcoronatracker.repositories;

import de.govhackathon.wvsvcoronatracker.model.Position;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
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
@ContextConfiguration(initializers = Spec_PositionsRepository.Initializer.class)
@Testcontainers
public class Spec_PositionsRepository {


    @ClassRule
    public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgis/postgis:11-3.0");

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgres.start();
            TestPropertyValues.of("spring.datasource.url="+postgres.getJdbcUrl(),
                    "spring.datasource.username="+postgres.getUsername(),"spring.datasource.password="+postgres.getPassword()).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private PositionsRepository repository;

    private OffsetDateTime now;

    @BeforeEach
    public void before(){
        repository.deleteAll();
        now = OffsetDateTime.now();
    }

    @Test
    public void testFindAll(){
        Position position1 = new Position();

        position1.setUserId("a-user-id");

        List<Position> actual = repository.findAll();

        Assertions.assertThat(actual).allMatch(position -> position.getId().equals(position1.getId()) && position.getUserId().equals("a-user-id"));
    }

    @Test
    public void testFindByUserIdAndTimestamp(){
        OffsetDateTime earlier = now.minusDays(2);
        String userId = "test";

        Position position1 = createByUserIdAndTimestamp(userId,now);
        Position position2 = createByUserIdAndTimestamp(userId,earlier);

        List<Position> actual = repository.findByUserIdAndTimestamp(userId, now);

        Assertions.assertThat(actual).contains(position1).doesNotContain(position2);

    }


    // oben links: 50.003062, 8.263453
    // unten rechts:49.993028, 8.280198

    @Test
    public void testFindByUserIdsAndPositionInGeometry() throws ParseException{

        Position position1 = this.createByUserIdAndTimestampAndPoint("test",now,50.000821, 8.268120);
        Geometry rectangle = new WKTReader().read("POLYGON ((50.003062 8.263453, 49.993028 8.263453 , 49.993028 8.280198 , 50.003062 8.280198, 50.003062 8.263453))");

        List<Position> actual = repository.findByTimestampBetweenAndPositionInGeometry(now, now,rectangle);

        Assertions.assertThat(actual).contains(position1);
    }


    @Test
    public void testFindByUserIdsAndPositionInGeometryPositionNotInGeometry() throws ParseException{

        this.createByUserIdAndTimestampAndPoint("test",now,50.034160, 8.223215);
        Geometry rectangle = new WKTReader().read("POLYGON ((50.003062 8.263453, 49.993028 8.263453 , 49.993028 8.280198 , 50.003062 8.280198, 50.003062 8.263453))");

        List<Position> actual = repository.findByTimestampBetweenAndPositionInGeometry(now, now,rectangle);

        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    public void testFindByUserIdsAndPositionInGeometryPositionTooEarly() throws ParseException{

        this.createByUserIdAndTimestampAndPoint("test",now.minusMinutes(1),50.034160, 8.223215);
        Geometry rectangle = new WKTReader().read("POLYGON ((50.003062 8.263453, 49.993028 8.263453 , 49.993028 8.280198 , 50.003062 8.280198, 50.003062 8.263453))");

        List<Position> actual = repository.findByTimestampBetweenAndPositionInGeometry(now, now.plusMinutes(60),rectangle);

        Assertions.assertThat(actual).isEmpty();
    }



    @Test
    public void testFindByUserIdsAndPositionInGeometryPositionTooLate() throws ParseException{

        this.createByUserIdAndTimestampAndPoint("test",now.plusMinutes(61),50.034160, 8.223215);
        Geometry rectangle = new WKTReader().read("POLYGON ((50.003062 8.263453, 49.993028 8.263453 , 49.993028 8.280198 , 50.003062 8.280198, 50.003062 8.263453))");

        List<Position> actual = repository.findByTimestampBetweenAndPositionInGeometry(now, now.plusMinutes(60),rectangle);

        Assertions.assertThat(actual).isEmpty();
    }


    @Test
    public void testFindByTimestamp(){
        OffsetDateTime earlier = now.minusDays(2);

        Position position1 = new Position();
        position1.setTimestamp(now);
        Position position2 = new Position();
        position2.setTimestamp(earlier);

        position1 = repository.save(position1);
        position2 = repository.save(position2);

        List<Position> actual = repository.findByTimestamp(now);
        Assertions.assertThat(actual).allMatch(position -> position.getTimestamp().isEqual(now));

    }



    @Test
    public void testFindByUserIdAndBetweenTimestamp(){
        OffsetDateTime earlier = now.minusDays(2);
        OffsetDateTime later = now.plusDays(1);
        OffsetDateTime earliest = now.minusDays(4);
        String userId = "test";

        Position position1 = createByUserIdAndTimestamp(userId,now);
        Position position2 = createByUserIdAndTimestamp(userId,earlier);
        Position position3 = createByUserIdAndTimestamp(userId,later);
        Position position4 = createByUserIdAndTimestamp(userId,earliest);

        List<Position> actual = repository.findByUserIdAndTimestampBetween(userId,earlier,later);
        Assertions.assertThat(actual).allMatch(position -> position.getUserId().equals("test") &&
                (position.getTimestamp().isEqual(earlier) ||
                        position.getTimestamp().isEqual(later) ||
                        position.getTimestamp().isEqual(now)));

    }


    private Position createByUserIdAndTimestamp(String userId, OffsetDateTime timestamp){
        Position position = new Position();
        position.setUserId(userId);
        position.setTimestamp(timestamp);
        position = repository.save(position);
        return position;
    }

    private Position createByUserIdAndTimestampAndPoint(String userId, OffsetDateTime timestamp,double latitude, double longitude) throws ParseException {
        Position position = new Position();
        position.setUserId(userId);
        position.setTimestamp(timestamp);

        Point point = (Point) new WKTReader().read("POINT ("+latitude+" "+ longitude+")");
        position.setLocation(point);
        position = repository.save(position);
        return position;
    }



}
