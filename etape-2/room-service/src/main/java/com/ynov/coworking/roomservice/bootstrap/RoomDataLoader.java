package com.ynov.coworking.roomservice.bootstrap;

import com.ynov.coworking.roomservice.model.Room;
import com.ynov.coworking.roomservice.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoomDataLoader implements CommandLineRunner {

  private final RoomRepository roomRepository;

  public RoomDataLoader(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @Override
  public void run(String... args) {
    if (roomRepository.count() == 0) {
      Room a = new Room();
      a.setName("A1");
      a.setAvailable(true);
      roomRepository.save(a);
      Room b = new Room();
      b.setName("B2");
      b.setAvailable(true);
      roomRepository.save(b);
    }
  }
}
