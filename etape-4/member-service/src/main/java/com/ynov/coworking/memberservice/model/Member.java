package com.ynov.coworking.memberservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private boolean suspended;
  private int maxConcurrentBookings = 2;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
