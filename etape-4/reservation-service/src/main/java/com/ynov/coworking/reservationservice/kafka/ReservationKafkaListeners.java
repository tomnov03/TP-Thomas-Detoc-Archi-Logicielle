package com.ynov.coworking.reservationservice.kafka;

import com.ynov.coworking.reservationservice.service.ReservationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationKafkaListeners {

  private final ReservationService reservationService;

  public ReservationKafkaListeners(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @KafkaListener(
      topics = "room-events",
      containerFactory = "reservationRoomListenerContainerFactory")
  public void onRoom(RoomEvent ev) {
    if (ev != null && "ROOM_DELETED".equals(ev.getType())) {
      reservationService.handleRoomDeleted(ev.getRoomId());
    }
  }

  @KafkaListener(
      topics = "member-events",
      containerFactory = "reservationMemberListenerContainerFactory")
  public void onMember(MemberEvent ev) {
    if (ev != null && "MEMBER_DELETED".equals(ev.getType())) {
      reservationService.handleMemberDeleted(ev.getMemberId());
    }
  }
}
