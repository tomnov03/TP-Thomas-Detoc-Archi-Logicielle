package com.ynov.coworking.roomservice.repository;

import com.ynov.coworking.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {}
