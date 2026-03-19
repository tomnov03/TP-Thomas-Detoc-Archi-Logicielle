package com.ynov.coworking.memberservice.dto;

public class MemberResponse {

  private Long id;
  private boolean suspended;
  private int maxConcurrentBookings;
  private String tier;

  public MemberResponse() {}

  public MemberResponse(Long id, boolean suspended, int maxConcurrentBookings, String tier) {
    this.id = id;
    this.suspended = suspended;
    this.maxConcurrentBookings = maxConcurrentBookings;
    this.tier = tier;
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

  public String getTier() {
    return tier;
  }

  public void setTier(String tier) {
    this.tier = tier;
  }
}
