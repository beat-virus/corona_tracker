package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.TemperatureMeasurement;
import de.govhackathon.wvsvcoronatracker.repositories.TemperatureMeasurementRepository;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import de.govhackathon.wvsvcoronatracker.services.BodyTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("bodyTempService")
public class BodyTempServiceImpl implements BodyTempService {

    private TemperatureMeasurementRepository temperatureMeasurementRepository;


    @Autowired
    public BodyTempServiceImpl(TemperatureMeasurementRepository temperatureMeasurementRepository, UserRepository userRepository) {
        this.temperatureMeasurementRepository = temperatureMeasurementRepository;
    }

    @Override
    public LocalDateTime getLastBodyTempByUser(String userId) {
        List<TemperatureMeasurement> measurements = this.temperatureMeasurementRepository.getTemperatureMeasurementsByUser_tokenOrderByTimeAsc(userId);
        return measurements.get(measurements.size() - 1).getTime();
    }

    @Override
    public TemperatureMeasurement addBodyTemperature(TemperatureMeasurement measurement) {
        return this.temperatureMeasurementRepository.save(measurement);
    }

}
