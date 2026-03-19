package com.ynov.coworking.roomservice.service;

import com.ynov.coworking.roomservice.dto.RoomResponse;
import com.ynov.coworking.roomservice.dto.RoomUpdateRequest;
import com.ynov.coworking.roomservice.kafka.RoomEvent;
import com.ynov.coworking.roomservice.model.Room;
import com.ynov.coworking.roomservice.repository.RoomRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

  private final RoomRepository roomRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public RoomService(
      RoomRepository roomRepository, KafkaTemplate<String, Object> kafkaTemplate) {
    this.roomRepository = roomRepository;
    this.kafkaTemplate = kafkaTemplate;
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
    kafkaTemplate.send("room-events", String.valueOf(id), new RoomEvent("ROOM_DELETED", id));
  }
}
