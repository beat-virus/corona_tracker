package de.govhackathon.wvsvcoronatracker.api;

import de.ghwct.service.api.MeasurementApi;
import de.ghwct.service.model.TempMeasurementDto;
import de.govhackathon.wvsvcoronatracker.model.mapper.TemperatureMeasurementMapper;
import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import de.govhackathon.wvsvcoronatracker.services.BodyTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = AppConfig.API_PATH)
public class MeasurementController implements MeasurementApi {

    private BodyTempService bodyTempService;
    private TemperatureMeasurementMapper mapper;

    @Autowired
    MeasurementController(BodyTempService bodyTempService, TemperatureMeasurementMapper temperatureMeasurementMapper) {
        this.bodyTempService = bodyTempService;
        this.mapper = temperatureMeasurementMapper;
    }

    @Override
    public ResponseEntity<TempMeasurementDto> createBodyTempMeasurement(TempMeasurementDto dto) {
        this.bodyTempService.addBodyTemperature(this.mapper.toEntity(dto));
        return ResponseEntity.ok(dto);
    }
}
