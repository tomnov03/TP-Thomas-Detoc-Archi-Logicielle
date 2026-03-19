package com.ynov.coworking.roomservice.dto;

public class RoomResponse {

  private Long id;
  private boolean available;

  public RoomResponse() {}

  public RoomResponse(Long id, boolean available) {
    this.id = id;
    this.available = available;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }
}
