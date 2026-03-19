package com.ynov.coworking.memberservice.dto;

public class MemberCreateRequest {

  private String email;
  private Integer maxConcurrentBookings;
  private String tier = "BASIC";

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getMaxConcurrentBookings() {
    return maxConcurrentBookings;
  }

  public void setMaxConcurrentBookings(Integer maxConcurrentBookings) {
    this.maxConcurrentBookings = maxConcurrentBookings;
  }

  public String getTier() {
    return tier;
  }

  public void setTier(String tier) {
    this.tier = tier;
  }
}
