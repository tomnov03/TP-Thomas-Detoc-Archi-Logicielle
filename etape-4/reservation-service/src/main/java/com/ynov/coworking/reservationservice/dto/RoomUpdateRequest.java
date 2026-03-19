package com.ynov.coworking.reservationservice.dto;

public class RoomUpdateRequest {

  private boolean available;

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }
}
