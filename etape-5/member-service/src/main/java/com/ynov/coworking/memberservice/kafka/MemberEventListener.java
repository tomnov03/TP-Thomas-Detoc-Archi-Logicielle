package com.ynov.coworking.memberservice.kafka;

import com.ynov.coworking.memberservice.service.MemberService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MemberEventListener {

  private final MemberService memberService;

  public MemberEventListener(MemberService memberService) {
    this.memberService = memberService;
  }

  @KafkaListener(
      topics = "member-events",
      containerFactory = "memberKafkaListenerContainerFactory")
  public void consume(MemberEvent ev) {
    if (ev == null || ev.getType() == null) {
      return;
    }
    if ("MEMBER_SUSPEND".equals(ev.getType())) {
      memberService.suspend(ev.getMemberId());
    } else if ("MEMBER_UNSUSPEND".equals(ev.getType())) {
      memberService.unsuspend(ev.getMemberId());
    }
  }
}
