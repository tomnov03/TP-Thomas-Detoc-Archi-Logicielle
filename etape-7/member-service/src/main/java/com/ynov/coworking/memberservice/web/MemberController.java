package com.ynov.coworking.memberservice.web;

import com.ynov.coworking.memberservice.dto.MemberCreateRequest;
import com.ynov.coworking.memberservice.dto.MemberResponse;
import com.ynov.coworking.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Members", description = "Manage coworking members")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a new member")
  public MemberResponse create(@RequestBody MemberCreateRequest body) {
    return memberService.create(body);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get member by id")
  public MemberResponse get(@PathVariable Long id) {
    return memberService.getById(id);
  }

  @GetMapping("/{id}/can-book")
  @Operation(summary = "Check if member can book a room")
  public boolean canBook(@PathVariable("id") Long memberId) {
    return memberService.canBook(memberId);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a member")
  public void delete(@PathVariable Long id) {
    memberService.delete(id);
  }
}
