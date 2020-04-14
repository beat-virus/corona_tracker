package de.govhackathon.wvsvcoronatracker.api;

import de.ghwct.service.api.HealthDataSetApi;
import de.ghwct.service.model.HealthDataSetDto;
import de.govhackathon.wvsvcoronatracker.model.HealthDataSet;
import de.govhackathon.wvsvcoronatracker.model.MedicalState;
import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.model.mapper.HealthDataSetMapper;
import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import de.govhackathon.wvsvcoronatracker.services.HealthDataSetService;
import de.govhackathon.wvsvcoronatracker.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

import static de.govhackathon.wvsvcoronatracker.api.UsersController.ERRMSG_USER_NOT_FOUND;


@RestController
@RequestMapping(value = AppConfig.API_PATH)
@AllArgsConstructor
public class HealthDataSetController implements HealthDataSetApi {

    private UsersService usersService;

    private HealthDataSetMapper healthDataSetMapper;

    private HealthDataSetService healthDataSetService;

    @Override
    public ResponseEntity<HealthDataSetDto> createDataSet(HealthDataSetDto healthDataSetDto) {
        try {
            User user = usersService.getUser(healthDataSetDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(ERRMSG_USER_NOT_FOUND));

            HealthDataSet healthDataSet = new HealthDataSet();
            healthDataSet.setUser(user);
            //TODO unify enums and Error handling
            healthDataSet.setMedicalState(MedicalState.valueOf(healthDataSetDto.getMedicalState().toString()));

            healthDataSet = healthDataSetService.addHealthDataSet(healthDataSet);

            return ResponseEntity.ok().body(healthDataSetMapper.toDto(healthDataSet));
        } catch (EntityNotFoundException | IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
