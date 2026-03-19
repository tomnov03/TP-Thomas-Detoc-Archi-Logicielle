package com.ynov.coworking.memberservice.bootstrap;

import com.ynov.coworking.memberservice.model.Member;
import com.ynov.coworking.memberservice.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MemberDataLoader implements CommandLineRunner {

  private final MemberRepository memberRepository;

  public MemberDataLoader(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public void run(String... args) {
    if (memberRepository.count() == 0) {
      Member m1 = new Member();
      m1.setEmail("a@b.c");
      m1.setSuspended(false);
      m1.setMaxConcurrentBookings(2);
      memberRepository.save(m1);
      Member m2 = new Member();
      m2.setEmail("x@y.z");
      m2.setSuspended(false);
      m2.setMaxConcurrentBookings(1);
      memberRepository.save(m2);
    }
  }
}
