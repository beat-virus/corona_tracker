package de.govhackathon.wvsvcoronatracker.api;

import de.ghwct.service.api.ReportsApi;
import de.ghwct.service.model.ReportTripsDto;
import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import de.govhackathon.wvsvcoronatracker.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = AppConfig.API_PATH)
public class ReportsController implements ReportsApi {

    @Autowired
    ReportsService reportsService;

    @Override
    public ResponseEntity<List<ReportTripsDto>> getLocations(OffsetDateTime from, OffsetDateTime to, List<String> deviceId) {
        // TODO implement me
        return ResponseEntity.ok().body(Collections.emptyList());
    }
}
