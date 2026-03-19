package com.ynov.coworking.roomservice.web;

import com.ynov.coworking.roomservice.dto.RoomCreateRequest;
import com.ynov.coworking.roomservice.dto.RoomResponse;
import com.ynov.coworking.roomservice.dto.RoomUpdateRequest;
import com.ynov.coworking.roomservice.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

  private final RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RoomResponse create(@RequestBody RoomCreateRequest body) {
    return roomService.create(body);
  }

  @GetMapping("/{id}")
  public RoomResponse get(@PathVariable Long id) {
    return roomService.getById(id);
  }

  @PutMapping("/{id}")
  public void put(@PathVariable Long id, @RequestBody RoomUpdateRequest body) {
    roomService.updateAvailability(id, body);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    roomService.delete(id);
  }
}
