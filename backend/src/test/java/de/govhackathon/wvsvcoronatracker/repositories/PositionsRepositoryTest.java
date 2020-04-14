package de.govhackathon.wvsvcoronatracker.repositories;

import de.govhackathon.wvsvcoronatracker.model.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PositionsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PositionsRepository repository;

    @BeforeEach
    public void before(){
        entityManager.clear();
    }

    @Test
    public void testFindAll(){
        Position position1 = new Position();

        position1.setUserId("a-user-id");
        entityManager.persist(position1);

        List<Position> actual = repository.findAll();

        Assertions.assertThat(actual).contains(position1);
    }

    @Test
    public void testFindByUserIdAndTimestamp(){
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime earlier = now.minusDays(2);
        String userId = "test";

        Position position1 = createByUserIdAndTimestamp(userId,now);
        Position position2 = createByUserIdAndTimestamp(userId,earlier);

        List<Position> actual = repository.findByUserIdAndTimestamp(userId, now);

        Assertions.assertThat(actual).contains(position1).doesNotContain(position2);

    }


    @Test
    public void testFindByTimestamp(){
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime earlier = now.minusDays(2);

        Position position1 = new Position();
        position1.setTimestamp(now);
        Position position2 = new Position();
        position2.setTimestamp(earlier);

        entityManager.persist(position1);
        entityManager.persist(position2);

        List<Position> actual = repository.findByTimestamp(now);

        Assertions.assertThat(actual).contains(position1).doesNotContain(position2);

    }



    @Test
    public void testFindByUserIdAndBetweenTimestamp(){
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime earlier = now.minusDays(2);
        OffsetDateTime later = now.plusDays(1);
        OffsetDateTime earliest = now.minusDays(4);
        String userId = "test";

        Position position1 = createByUserIdAndTimestamp(userId,now);
        Position position2 = createByUserIdAndTimestamp(userId,earlier);
        Position position3 = createByUserIdAndTimestamp(userId,later);
        Position position4 = createByUserIdAndTimestamp(userId,earliest);

        List<Position> actual = repository.findByUserIdAndTimestampBetween(userId,earlier,later);

        Assertions.assertThat(actual).contains(position1,position2,position3).doesNotContain(position4);

    }


    private Position createByUserIdAndTimestamp(String userId, OffsetDateTime timestamp){
        Position position = new Position();
        position.setUserId(userId);
        position.setTimestamp(timestamp);
        entityManager.persist(position);
        return position;
    }


}
