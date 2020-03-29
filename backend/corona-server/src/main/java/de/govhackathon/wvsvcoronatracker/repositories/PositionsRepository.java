package de.govhackathon.wvsvcoronatracker.repositories;

import de.govhackathon.wvsvcoronatracker.model.Position;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PositionsRepository extends JpaRepository<Position,Integer> {

    List<Position> findByUserId(String userId);

    List<Position> findByTimestamp(OffsetDateTime timestamp);


    List<Position> findByUserIdAndTimestamp(String userId, OffsetDateTime from);

    List<Position> findByUserIdAndTimestampBetween(String userId, OffsetDateTime min, OffsetDateTime max);


    @Query(value = "SELECT pos FROM Position pos WHERE within(pos.location, :bounds) = true AND pos.timestamp >= :start AND pos.timestamp <= :end")
    List<Position> findByTimestampBetweenAndPositionInGeometry(@Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end, @Param("bounds") Geometry geometry);

}
