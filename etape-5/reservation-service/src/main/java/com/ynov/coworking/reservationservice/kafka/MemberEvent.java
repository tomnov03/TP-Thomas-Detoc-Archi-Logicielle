package com.ynov.coworking.reservationservice.kafka;

public class MemberEvent {

  private String type;
  private Long memberId;

  public MemberEvent() {}

  public MemberEvent(String type, Long memberId) {
    this.type = type;
    this.memberId = memberId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }
}
