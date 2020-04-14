package de.govhackathon.wvsvcoronatracker.repositories;

import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import org.springframework.data.repository.CrudRepository;

public interface AppConfigRepository extends CrudRepository<AppConfig, String> {
}
