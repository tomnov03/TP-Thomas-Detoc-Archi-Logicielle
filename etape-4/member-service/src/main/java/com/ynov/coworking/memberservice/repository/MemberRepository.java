package com.ynov.coworking.memberservice.repository;

import com.ynov.coworking.memberservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {}
