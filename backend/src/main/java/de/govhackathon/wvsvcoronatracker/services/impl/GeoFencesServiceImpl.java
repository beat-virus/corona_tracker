package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.GeoFence;
import de.govhackathon.wvsvcoronatracker.repositories.GeoFenceRepository;
import de.govhackathon.wvsvcoronatracker.services.GeoFencesService;
import java.util.List;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeoFencesServiceImpl implements GeoFencesService {

    @Autowired
    GeoFenceRepository geoFenceRepository;

    @Override
    public List<GeoFence> getGeoFences() {
        return IterableUtils.toList(geoFenceRepository.findAll());
    }

    @Override
    public GeoFence createGeoFence(final GeoFence geofence) {
        return geoFenceRepository.save(geofence);
    }

    @Override
    public GeoFence updateGeoFence(final GeoFence geoFence) {
        return geoFenceRepository.save(geoFence);
    }

    @Override
    public void deleteGeoFence(final GeoFence geoFence) {
        geoFenceRepository.delete(geoFence);
    }
}
