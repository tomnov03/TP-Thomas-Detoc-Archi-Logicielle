package com.ynov.coworking.roomservice.web;

import com.ynov.coworking.roomservice.dto.RoomResponse;
import com.ynov.coworking.roomservice.dto.RoomUpdateRequest;
import com.ynov.coworking.roomservice.service.RoomService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController {

  private final RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
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
