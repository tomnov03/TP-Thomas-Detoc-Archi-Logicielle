package com.ynov.coworking.memberservice.dto;

public class MemberResponse {

  private Long id;
  private boolean suspended;
  private int maxConcurrentBookings;

  public MemberResponse() {}

  public MemberResponse(Long id, boolean suspended, int maxConcurrentBookings) {
    this.id = id;
    this.suspended = suspended;
    this.maxConcurrentBookings = maxConcurrentBookings;
  }

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
