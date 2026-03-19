package com.ynov.coworking.reservationservice.web;

import com.ynov.coworking.reservationservice.dto.ReservationRequest;
import com.ynov.coworking.reservationservice.model.Reservation;
import com.ynov.coworking.reservationservice.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Reservation create(@RequestBody ReservationRequest body) {
    return reservationService.createReservation(body);
  }

  @PutMapping("/{id}/cancel")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancel(@PathVariable Long id) {
    reservationService.cancelReservation(id);
  }

  @PutMapping("/{id}/complete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void complete(@PathVariable Long id) {
    reservationService.completeReservation(id);
  }
}
