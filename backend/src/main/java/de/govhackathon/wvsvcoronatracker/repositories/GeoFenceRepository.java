package de.govhackathon.wvsvcoronatracker.repositories;

import de.govhackathon.wvsvcoronatracker.model.GeoFence;
import org.springframework.data.repository.CrudRepository;

public interface GeoFenceRepository extends CrudRepository<GeoFence, String> {

}
