package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.TemperatureMeasurement;

import java.time.LocalDateTime;

public interface BodyTempService {

  /**
   * Get the timestamp of last reported temperature.
   */
  LocalDateTime getLastBodyTempByUser(String userId);

  /**
   * Add new self reported temperature for user.
   */
  TemperatureMeasurement addBodyTemperature(TemperatureMeasurement measurement);
}
