package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.GeoFence;
import java.util.List;

public interface GeoFencesService {

    /**
     * Get all geo fences
     *
     * @return List of all geo fences, empty collection if none available
     */
    List<GeoFence> getGeoFences();

    /**
     * Create a geo fence
     *
     * @param geofence data to create geo fence from
     * @return Created geo fence
     */
    GeoFence createGeoFence(final GeoFence geofence);

    /**
     * Update a geo fence
     *
     * @param geoFence geo fence data to be changed
     * @return Updated geo fence
     */
    GeoFence updateGeoFence(final GeoFence geoFence);

    /**
     * Delete a geo fence
     *
     * @param geoFence geo fence to delete
     */
    void deleteGeoFence(final GeoFence geoFence);
}
