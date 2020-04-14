package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.Position;

import java.time.OffsetDateTime;
import java.util.List;

public interface PositionsService {

    /**
     * Save given position
     *
     * @return Saved position
     */
    Position savePosition(Position position);

    /**
     * Get all positions
     *
     * @return List of all positions, empty collection if none available
     */
    List<Position> getPositions();

    /**
     * Get all positions starting from date
     *
     * @param from date to get positions from
     * @return List of all positions, starting from date specified, empty collection if none available
     */
    List<Position> getPositionsByTimestamp(final OffsetDateTime from);


    /**
     * Get all users positions
     *
     * @param userId users id
     * @return List of all positions for a specific user, empty collection if none available
     */
    List<Position> getPositionsByUserId(final String userId);

    /**
     * Get all users positions starting from date and the user id
     *
     * @param userId users id
     * @param timestamp   date to get positions from
     * @return List of all positions for a specific user, starting from date specified, empty collection if none available
     */
    List<Position> getPositionsByUserIdAndTimeStamp(final String userId, final OffsetDateTime timestamp);


}
