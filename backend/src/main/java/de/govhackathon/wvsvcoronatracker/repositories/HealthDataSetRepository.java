package de.govhackathon.wvsvcoronatracker.repositories;

import de.govhackathon.wvsvcoronatracker.model.HealthDataSet;
import de.govhackathon.wvsvcoronatracker.model.MedicalState;
import de.govhackathon.wvsvcoronatracker.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface HealthDataSetRepository extends CrudRepository<HealthDataSet, String> {

    List<HealthDataSet> findByMedicalStateAndCreationDateBetween(MedicalState medicalState, OffsetDateTime before, OffsetDateTime after);

    List<HealthDataSet> findByUserAndMedicalStateAndCreationDateBetween(User user,MedicalState state, OffsetDateTime before, OffsetDateTime after);
}
