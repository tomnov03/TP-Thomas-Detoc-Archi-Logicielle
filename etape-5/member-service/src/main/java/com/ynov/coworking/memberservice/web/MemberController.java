package com.ynov.coworking.memberservice.web;

import com.ynov.coworking.memberservice.dto.MemberCreateRequest;
import com.ynov.coworking.memberservice.dto.MemberResponse;
import com.ynov.coworking.memberservice.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MemberResponse create(@RequestBody MemberCreateRequest body) {
    return memberService.create(body);
  }

  @GetMapping("/{id}")
  public MemberResponse get(@PathVariable Long id) {
    return memberService.getById(id);
  }

  @GetMapping("/{id}/can-book")
  public boolean canBook(@PathVariable("id") Long memberId) {
    return memberService.canBook(memberId);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    memberService.delete(id);
  }
}
