package com.ynov.coworking.reservationservice.dto;

public class MemberDto {

  private Long id;
  private boolean suspended;
  private int maxConcurrentBookings;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isSuspended() {
    return suspended;
  }

  public void setSuspended(boolean suspended) {
    this.suspended = suspended;
  }

  public int getMaxConcurrentBookings() {
    return maxConcurrentBookings;
  }

  public void setMaxConcurrentBookings(int maxConcurrentBookings) {
    this.maxConcurrentBookings = maxConcurrentBookings;
  }
}
