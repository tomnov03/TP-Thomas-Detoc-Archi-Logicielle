package com.ynov.coworking.reservationservice.repository;

import com.ynov.coworking.reservationservice.model.Reservation;
import com.ynov.coworking.reservationservice.model.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  @Query(
      "SELECT COUNT(r) FROM Reservation r WHERE r.roomId = :roomId AND r.status = :status "
          + "AND r.startDateTime < :endDateTime AND r.endDateTime > :startDateTime")
  long countOverlappingConfirmed(
      @Param("roomId") Long roomId,
      @Param("status") ReservationStatus status,
      @Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);

  List<Reservation> findByRoomIdAndStatus(Long roomId, ReservationStatus status);

  void deleteByMemberId(Long memberId);

  long countByMemberIdAndStatus(Long memberId, ReservationStatus status);
}
