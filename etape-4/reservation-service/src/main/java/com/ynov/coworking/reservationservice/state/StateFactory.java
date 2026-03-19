package com.ynov.coworking.reservationservice.state;

import com.ynov.coworking.reservationservice.model.ReservationStatus;

public final class StateFactory {

  private StateFactory() {}

  public static ReservationState getState(ReservationStatus status) {
    if (status == null) {
      throw new IllegalStateException("Unknown");
    }
    switch (status) {
      case CONFIRMED:
        return new ConfirmedState();
      case CANCELLED:
        return new CancelledState();
      case COMPLETED:
        return new CompletedState();
      default:
        throw new IllegalStateException("Unknown");
    }
  }
}
