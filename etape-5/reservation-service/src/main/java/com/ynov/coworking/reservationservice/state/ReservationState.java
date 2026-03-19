package com.ynov.coworking.reservationservice.state;

import com.ynov.coworking.reservationservice.model.Reservation;
import com.ynov.coworking.reservationservice.service.ReservationService;

public interface ReservationState {

  void cancel(Reservation reservation, ReservationService service);

  void complete(Reservation reservation, ReservationService service);
}
