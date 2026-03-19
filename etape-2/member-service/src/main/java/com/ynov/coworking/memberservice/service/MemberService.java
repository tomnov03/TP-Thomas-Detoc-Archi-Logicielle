package com.ynov.coworking.memberservice.service;

import com.ynov.coworking.memberservice.dto.MemberResponse;
import com.ynov.coworking.memberservice.model.Member;
import com.ynov.coworking.memberservice.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public MemberResponse getById(Long id) {
    Member m = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    return new MemberResponse(m.getId(), m.isSuspended(), m.getMaxConcurrentBookings());
  }

  public boolean canBook(Long id) {
    Member m = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    return !m.isSuspended();
  }

  @Transactional
  public void delete(Long id) {
    if (!memberRepository.existsById(id)) {
      throw new RuntimeException("Member not found");
    }
    memberRepository.deleteById(id);
  }
}
