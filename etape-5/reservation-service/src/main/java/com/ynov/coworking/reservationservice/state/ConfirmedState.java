package com.ynov.coworking.reservationservice.state;

import com.ynov.coworking.reservationservice.model.Reservation;
import com.ynov.coworking.reservationservice.service.ReservationService;

public class ConfirmedState implements ReservationState {

  @Override
  public void cancel(Reservation reservation, ReservationService service) {
    service.performCancelConfirmed(reservation);
  }

  @Override
  public void complete(Reservation reservation, ReservationService service) {
    service.performCompleteConfirmed(reservation);
  }
}
