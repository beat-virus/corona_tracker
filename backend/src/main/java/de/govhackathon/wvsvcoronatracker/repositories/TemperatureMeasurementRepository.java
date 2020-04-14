package de.govhackathon.wvsvcoronatracker.repositories;

import de.govhackathon.wvsvcoronatracker.model.TemperatureMeasurement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TemperatureMeasurementRepository extends CrudRepository<TemperatureMeasurement, String> {

    List<TemperatureMeasurement> getTemperatureMeasurementsByUser_tokenOrderByTimeAsc(String userId);
}
