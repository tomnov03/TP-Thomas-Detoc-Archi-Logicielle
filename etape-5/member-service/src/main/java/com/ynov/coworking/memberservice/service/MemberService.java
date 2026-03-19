package com.ynov.coworking.memberservice.service;

import java.util.Objects;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynov.coworking.memberservice.dto.MemberCreateRequest;
import com.ynov.coworking.memberservice.dto.MemberResponse;
import com.ynov.coworking.memberservice.kafka.MemberEvent;
import com.ynov.coworking.memberservice.model.Member;
import com.ynov.coworking.memberservice.repository.MemberRepository;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public MemberService(
      MemberRepository memberRepository, KafkaTemplate<String, Object> kafkaTemplate) {
    this.memberRepository = memberRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Transactional
  public MemberResponse create(MemberCreateRequest dto) {
    Member m = new Member();
    m.setEmail(Objects.requireNonNull(dto.getEmail(), "email"));
    Integer requestedMax = dto.getMaxConcurrentBookings();
    int max = (requestedMax == null) ? 2 : requestedMax;
    m.setMaxConcurrentBookings(max);
    String tier = dto.getTier() != null ? dto.getTier() : "BASIC";
    m.setTier(tier);
    m.setSuspended(false);
    Member saved = memberRepository.save(m);
    return new MemberResponse(
        saved.getId(), saved.isSuspended(), saved.getMaxConcurrentBookings(), saved.getTier());
  }

  public MemberResponse getById(Long id) {
    Long nid = Objects.requireNonNull(id, "id");
    Member m =
        memberRepository.findById(nid).orElseThrow(() -> new RuntimeException("Member not found"));
    return new MemberResponse(
        m.getId(), m.isSuspended(), m.getMaxConcurrentBookings(), m.getTier());
  }

  public boolean canBook(Long id) {
    Long nid = Objects.requireNonNull(id, "id");
    Member m =
        memberRepository.findById(nid).orElseThrow(() -> new RuntimeException("Member not found"));
    return !m.isSuspended();
  }

  @Transactional
  public void suspend(Long memberId) {
    Long mid = Objects.requireNonNull(memberId, "memberId");
    memberRepository
        .findById(mid)
        .ifPresent(
            m -> {
              m.setSuspended(true);
              memberRepository.save(m);
            });
  }

  @Transactional
  public void unsuspend(Long memberId) {
    Long mid = Objects.requireNonNull(memberId, "memberId");
    memberRepository
        .findById(mid)
        .ifPresent(
            m -> {
              m.setSuspended(false);
              memberRepository.save(m);
            });
  }

  @Transactional
  public void delete(Long id) {
    Long nid = Objects.requireNonNull(id, "id");
    if (!memberRepository.existsById(nid)) {
      throw new RuntimeException("Member not found");
    }
    kafkaTemplate.send(
        "member-events", Objects.requireNonNull(nid.toString()), new MemberEvent("MEMBER_DELETED", nid));
    memberRepository.deleteById(nid);
  }
}
