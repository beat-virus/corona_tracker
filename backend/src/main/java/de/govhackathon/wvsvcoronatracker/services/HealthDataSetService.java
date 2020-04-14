package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.HealthDataSet;

public interface HealthDataSetService {

    /**
     * adds a health data set to repo
     */
    HealthDataSet addHealthDataSet(HealthDataSet healthDataSet);
}
