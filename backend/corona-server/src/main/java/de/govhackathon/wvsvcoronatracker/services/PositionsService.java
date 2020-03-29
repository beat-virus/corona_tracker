package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.Position;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

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

    /**
     * Get all positions between two dates and inside the rectangle specified with the two coordinates.
     * The rectangle will be created by two diagonal points.
     *
     * @param before the start datetime
     * @param after the end datetime
     * @param latitude1 Latitude of Point 1
     * @param longitude1 Longitude of Point 1
     * @param latitude2 Latitude of Point 2
     * @param longitude2 Longitude of Point 2
     * @return List of all positions between a start and end date and inside the given rectangle.
     */
    List<Position> getPositionsByTimestampBetweenAndInRectangle(OffsetDateTime before, OffsetDateTime after, double latitude1, double longitude1, double latitude2, double longitude2);

    /**
     * Get all positions not older than a specific amount of minutes,  older than the current time
     * and inside a rectangle with the specified two coordinates.
     *
     * @param minutes Amount of minutes to check
     * @param latitude1 Latitude of Point 1
     * @param longitude1 Longitude of Point 1
     * @param latitude2 Latitude of Point 2
     * @param longitude2 Longitude of Point 2
     * @return List of all positions between a start and end date and inside the given rectangle.
     */
    List<Position> getCurrentPositionByTimestampNotOlderThanMinutesAndInRectangle(long minutes, double latitude1, double longitude1, double latitude2, double longitude2);

    default Geometry createRectangle(double latitude1, double longitude1, double latitude2, double longitude2){
        StringBuilder builder = new StringBuilder();
        builder.append("POLYGON ((");
        // Point 1
        builder.append(latitude1);
        builder.append(" ");
        builder.append(longitude1);
        builder.append(" , ");
        //Point 2
        builder.append(latitude2);
        builder.append(" ");
        builder.append(longitude1);
        builder.append(" , ");

        //Point 3
        builder.append(latitude2);
        builder.append(" ");
        builder.append(longitude2);
        builder.append(" , ");

        //Point 4
        builder.append(latitude1);
        builder.append(" ");
        builder.append(longitude2);
        builder.append(" , ");


        //Finalising Rectangle with Point 1;
        builder.append(latitude1);
        builder.append(" ");
        builder.append(longitude1);
        builder.append(" ))");
        try{
            Geometry result = new WKTReader().read(builder.toString());
            return result;
        }
        catch(ParseException e){
            return null;
        }
    }


    default Point createPoint(double latitude, double longitude){
        StringBuilder builder = new StringBuilder();
        builder.append("POINT (")
                .append(latitude)
                .append(" ")
                .append(longitude)
                .append(")");
        try {
            Point result = (Point) new WKTReader().read(builder.toString());
            return result;
        }
        catch(ParseException e){
            return null;
        }
    }


}
