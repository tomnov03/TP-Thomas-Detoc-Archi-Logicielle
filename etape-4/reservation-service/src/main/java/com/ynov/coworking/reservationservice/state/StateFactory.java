package com.ynov.coworking.reservationservice.state;

import com.ynov.coworking.reservationservice.model.ReservationStatus;

public final class StateFactory {

  private StateFactory() {}

  public static ReservationState getState(ReservationStatus status) {
    if (status == null) {
      throw new IllegalStateException("Unknown");
    }
    return switch (status) {
      case CONFIRMED -> new ConfirmedState();
      case CANCELLED -> new CancelledState();
      case COMPLETED -> new CompletedState();
    };
  }
}
