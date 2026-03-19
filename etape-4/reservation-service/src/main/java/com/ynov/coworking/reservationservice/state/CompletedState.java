package com.ynov.coworking.reservationservice.state;

import com.ynov.coworking.reservationservice.model.Reservation;
import com.ynov.coworking.reservationservice.service.ReservationService;

public class CompletedState implements ReservationState {

  @Override
  public void cancel(Reservation reservation, ReservationService service) {
    throw new IllegalStateException("Reservation already completed");
  }

  @Override
  public void complete(Reservation reservation, ReservationService service) {
    throw new IllegalStateException("Reservation already completed");
  }
}
