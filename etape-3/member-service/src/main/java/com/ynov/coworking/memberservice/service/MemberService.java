package com.ynov.coworking.memberservice.service;

import com.ynov.coworking.memberservice.dto.MemberResponse;
import com.ynov.coworking.memberservice.kafka.MemberEvent;
import com.ynov.coworking.memberservice.model.Member;
import com.ynov.coworking.memberservice.repository.MemberRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public MemberService(
      MemberRepository memberRepository, KafkaTemplate<String, Object> kafkaTemplate) {
    this.memberRepository = memberRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  public MemberResponse getById(Long id) {
    Member m =
        memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    return new MemberResponse(m.getId(), m.isSuspended(), m.getMaxConcurrentBookings());
  }

  public boolean canBook(Long id) {
    Member m =
        memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    return !m.isSuspended();
  }

  @Transactional
  public void suspend(Long memberId) {
    memberRepository
        .findById(memberId)
        .ifPresent(
            m -> {
              m.setSuspended(true);
              memberRepository.save(m);
            });
  }

  @Transactional
  public void unsuspend(Long memberId) {
    memberRepository
        .findById(memberId)
        .ifPresent(
            m -> {
              m.setSuspended(false);
              memberRepository.save(m);
            });
  }

  @Transactional
  public void delete(Long id) {
    if (!memberRepository.existsById(id)) {
      throw new RuntimeException("Member not found");
    }
    kafkaTemplate.send("member-events", String.valueOf(id), new MemberEvent("MEMBER_DELETED", id));
    memberRepository.deleteById(id);
  }
}
