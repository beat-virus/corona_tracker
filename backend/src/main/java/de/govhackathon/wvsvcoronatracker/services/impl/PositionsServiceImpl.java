package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.Position;
import de.govhackathon.wvsvcoronatracker.repositories.PositionsRepository;
import de.govhackathon.wvsvcoronatracker.services.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PositionsServiceImpl implements PositionsService {

  @Autowired
  private PositionsRepository positionsRepository;

  @Override
  public Position savePosition(Position position) {
    return positionsRepository.save(position);
  }

  @Override
  public List<Position> getPositions() {
    return positionsRepository.findAll();
  }

  @Override
  public List<Position> getPositionsByTimestamp(OffsetDateTime timestamp) {
    return positionsRepository.findByTimestamp(timestamp);
  }

  @Override
  public List<Position> getPositionsByUserId(final String userId) {
    return positionsRepository.findByUserId(userId);
  }

  @Override
  public List<Position> getPositionsByUserIdAndTimeStamp(String userId, OffsetDateTime timestamp) {
    return positionsRepository.findByUserIdAndTimestamp(userId, timestamp);
  }


}
