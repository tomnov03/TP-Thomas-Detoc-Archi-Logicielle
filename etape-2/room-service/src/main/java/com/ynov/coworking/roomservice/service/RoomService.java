package com.ynov.coworking.roomservice.service;

import com.ynov.coworking.roomservice.dto.RoomResponse;
import com.ynov.coworking.roomservice.dto.RoomUpdateRequest;
import com.ynov.coworking.roomservice.model.Room;
import com.ynov.coworking.roomservice.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

  private final RoomRepository roomRepository;

  public RoomService(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  public RoomResponse getById(Long id) {
    Room r = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    return new RoomResponse(r.getId(), r.isAvailable());
  }

  @Transactional
  public void updateAvailability(Long id, RoomUpdateRequest req) {
    Room r = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    r.setAvailable(req.isAvailable());
    roomRepository.save(r);
  }

  @Transactional
  public void delete(Long id) {
    if (!roomRepository.existsById(id)) {
      throw new RuntimeException("Room not found");
    }
    roomRepository.deleteById(id);
  }
}
