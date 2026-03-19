package com.ynov.coworking.reservationservice.kafka;

public class RoomEvent {

  private String type;
  private Long roomId;

  public RoomEvent() {}

  public RoomEvent(String type, Long roomId) {
    this.type = type;
    this.roomId = roomId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }
}
