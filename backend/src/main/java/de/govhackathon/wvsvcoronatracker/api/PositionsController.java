package de.govhackathon.wvsvcoronatracker.api;

import de.ghwct.service.api.PositionsApi;
import de.ghwct.service.model.PositionDto;
import de.govhackathon.wvsvcoronatracker.model.Position;
import de.govhackathon.wvsvcoronatracker.model.mapper.PositionMapper;
import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import de.govhackathon.wvsvcoronatracker.services.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = AppConfig.API_PATH)
public class PositionsController implements PositionsApi {

    private PositionsService positionsService;
    private PositionMapper positionMapper;

    @Autowired
    PositionsController(PositionsService positionsService, PositionMapper positionMapper) {
        this.positionsService = positionsService;
        this.positionMapper = positionMapper;
    }

    @Override
    public ResponseEntity<PositionDto> createPosition(PositionDto positionDto, String userId) {
        final Position createdPos = this.positionsService.savePosition(positionMapper.toEntity(positionDto));
        // TODO add position to User
        return ResponseEntity.ok().body(this.positionMapper.toDto(createdPos));
    }

    @Override
    public ResponseEntity<List<PositionDto>> getPositions(String userId, OffsetDateTime from, OffsetDateTime to, String id) {

        return ResponseEntity.ok().body(positionsService.getPositions().stream().map(position -> this.positionMapper.toDto(position)).collect(Collectors.toList()));
    }
}
