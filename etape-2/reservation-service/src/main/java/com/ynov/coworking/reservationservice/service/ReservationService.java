package com.ynov.coworking.reservationservice.service;

import com.ynov.coworking.reservationservice.dto.ReservationRequest;
import com.ynov.coworking.reservationservice.dto.RoomDto;
import com.ynov.coworking.reservationservice.dto.RoomUpdateRequest;
import com.ynov.coworking.reservationservice.model.Reservation;
import com.ynov.coworking.reservationservice.model.ReservationStatus;
import com.ynov.coworking.reservationservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final RestTemplate restTemplate;

  public ReservationService(
      ReservationRepository reservationRepository, RestTemplate restTemplate) {
    this.reservationRepository = reservationRepository;
    this.restTemplate = restTemplate;
  }

  public Reservation createReservation(ReservationRequest dto) {
    Boolean canBook =
        restTemplate.getForObject(
            "http://member-service/members/{memberId}/can-book",
            Boolean.class,
            dto.getMemberId());
    if (Boolean.FALSE.equals(canBook)) {
      throw new RuntimeException("Member is suspended");
    }
    RoomDto room =
        restTemplate.getForObject(
            "http://room-service/rooms/{roomId}", RoomDto.class, dto.getRoomId());
    if (room == null || !room.isAvailable()) {
      throw new RuntimeException("Room is not available");
    }
    long overlap =
        reservationRepository.countOverlappingConfirmed(
            dto.getRoomId(),
            ReservationStatus.CONFIRMED,
            dto.getStartDateTime(),
            dto.getEndDateTime());
    if (overlap > 0) {
      throw new RuntimeException("Slot overlaps existing reservation");
    }
    Reservation r = new Reservation();
    r.setRoomId(dto.getRoomId());
    r.setMemberId(dto.getMemberId());
    r.setStartDateTime(dto.getStartDateTime());
    r.setEndDateTime(dto.getEndDateTime());
    r.setStatus(ReservationStatus.CONFIRMED);
    Reservation saved = reservationRepository.save(r);
    RoomUpdateRequest off = new RoomUpdateRequest();
    off.setAvailable(false);
    restTemplate.put("http://room-service/rooms/{roomId}", off, dto.getRoomId());
    return saved;
  }

  public void cancelReservation(Long id) {
    Reservation r =
        reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    if (r.getStatus() != ReservationStatus.CONFIRMED) {
      throw new RuntimeException("Invalid status");
    }
    r.setStatus(ReservationStatus.CANCELLED);
    reservationRepository.save(r);
    RoomUpdateRequest on = new RoomUpdateRequest();
    on.setAvailable(true);
    restTemplate.put("http://room-service/rooms/{roomId}", on, r.getRoomId());
  }

  public void completeReservation(Long id) {
    Reservation r =
        reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    if (r.getStatus() != ReservationStatus.CONFIRMED) {
      throw new RuntimeException("Invalid status");
    }
    r.setStatus(ReservationStatus.COMPLETED);
    reservationRepository.save(r);
    RoomUpdateRequest on = new RoomUpdateRequest();
    on.setAvailable(true);
    restTemplate.put("http://room-service/rooms/{roomId}", on, r.getRoomId());
  }
}
