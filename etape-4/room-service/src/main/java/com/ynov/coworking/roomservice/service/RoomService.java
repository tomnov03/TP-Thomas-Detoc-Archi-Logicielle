package com.ynov.coworking.roomservice.service;

import com.ynov.coworking.roomservice.dto.RoomResponse;
import com.ynov.coworking.roomservice.dto.RoomUpdateRequest;
import com.ynov.coworking.roomservice.kafka.RoomEvent;
import com.ynov.coworking.roomservice.model.Room;
import com.ynov.coworking.roomservice.repository.RoomRepository;
import java.util.Objects;
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
    Long nid = Objects.requireNonNull(id, "id");
    Room r =
        roomRepository.findById(nid).orElseThrow(() -> new RuntimeException("Room not found"));
    return new RoomResponse(r.getId(), r.isAvailable());
  }

  @Transactional
  public void updateAvailability(Long id, RoomUpdateRequest req) {
    Long nid = Objects.requireNonNull(id, "id");
    Room r =
        roomRepository.findById(nid).orElseThrow(() -> new RuntimeException("Room not found"));
    r.setAvailable(req.isAvailable());
    roomRepository.save(r);
  }

  @Transactional
  public void delete(Long id) {
    Long nid = Objects.requireNonNull(id, "id");
    if (!roomRepository.existsById(nid)) {
      throw new RuntimeException("Room not found");
    }
    roomRepository.deleteById(nid);
    kafkaTemplate.send(
        "room-events",
        Objects.requireNonNull(nid.toString()),
        new RoomEvent("ROOM_DELETED", nid));
  }
}
