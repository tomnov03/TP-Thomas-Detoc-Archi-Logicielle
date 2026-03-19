package com.ynov.coworking.reservationservice.service;

import com.ynov.coworking.reservationservice.dto.MemberDto;
import com.ynov.coworking.reservationservice.dto.ReservationRequest;
import com.ynov.coworking.reservationservice.dto.RoomDto;
import com.ynov.coworking.reservationservice.dto.RoomUpdateRequest;
import com.ynov.coworking.reservationservice.kafka.MemberEvent;
import com.ynov.coworking.reservationservice.model.Reservation;
import com.ynov.coworking.reservationservice.model.ReservationStatus;
import com.ynov.coworking.reservationservice.repository.ReservationRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final RestTemplate restTemplate;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public ReservationService(
      ReservationRepository reservationRepository,
      RestTemplate restTemplate,
      KafkaTemplate<String, Object> kafkaTemplate) {
    this.reservationRepository = reservationRepository;
    this.restTemplate = restTemplate;
    this.kafkaTemplate = kafkaTemplate;
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
    maybeSuspendAfterBooking(dto.getMemberId());
    return saved;
  }

  public void cancelReservation(Long id) {
    Reservation r =
        reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    if (r.getStatus() != ReservationStatus.CONFIRMED) {
      throw new RuntimeException("Invalid status");
    }
    Long memberId = r.getMemberId();
    r.setStatus(ReservationStatus.CANCELLED);
    reservationRepository.save(r);
    RoomUpdateRequest on = new RoomUpdateRequest();
    on.setAvailable(true);
    restTemplate.put("http://room-service/rooms/{roomId}", on, r.getRoomId());
    maybeUnsuspendAfterChange(memberId);
  }

  public void completeReservation(Long id) {
    Reservation r =
        reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    if (r.getStatus() != ReservationStatus.CONFIRMED) {
      throw new RuntimeException("Invalid status");
    }
    Long memberId = r.getMemberId();
    r.setStatus(ReservationStatus.COMPLETED);
    reservationRepository.save(r);
    RoomUpdateRequest on = new RoomUpdateRequest();
    on.setAvailable(true);
    restTemplate.put("http://room-service/rooms/{roomId}", on, r.getRoomId());
    maybeUnsuspendAfterChange(memberId);
  }

  @Transactional
  public void handleRoomDeleted(Long roomId) {
    List<Reservation> list =
        reservationRepository.findByRoomIdAndStatus(roomId, ReservationStatus.CONFIRMED);
    Set<Long> memberIds = new HashSet<>();
    for (Reservation x : list) {
      memberIds.add(x.getMemberId());
      x.setStatus(ReservationStatus.CANCELLED);
      reservationRepository.save(x);
    }
    for (Long memberId : memberIds) {
      maybeUnsuspendAfterChange(memberId);
    }
  }

  @Transactional
  public void handleMemberDeleted(Long memberId) {
    reservationRepository.deleteByMemberId(memberId);
  }

  private void maybeSuspendAfterBooking(Long memberId) {
    long cnt =
        reservationRepository.countByMemberIdAndStatus(memberId, ReservationStatus.CONFIRMED);
    try {
      MemberDto m =
          restTemplate.getForObject(
              "http://member-service/members/{id}", MemberDto.class, memberId);
      if (m != null && cnt >= m.getMaxConcurrentBookings()) {
        kafkaTemplate.send(
            "member-events",
            String.valueOf(memberId),
            new MemberEvent("MEMBER_SUSPEND", memberId));
      }
    } catch (RuntimeException ignored) {
    }
  }

  private void maybeUnsuspendAfterChange(Long memberId) {
    long cnt =
        reservationRepository.countByMemberIdAndStatus(memberId, ReservationStatus.CONFIRMED);
    try {
      MemberDto m =
          restTemplate.getForObject(
              "http://member-service/members/{id}", MemberDto.class, memberId);
      if (m != null && cnt < m.getMaxConcurrentBookings()) {
        kafkaTemplate.send(
            "member-events",
            String.valueOf(memberId),
            new MemberEvent("MEMBER_UNSUSPEND", memberId));
      }
    } catch (RuntimeException ignored) {
    }
  }
}
