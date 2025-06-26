package com.taass.booking_service.model;

public enum BookingStatus {
    INVALID,
    FAILED,
    PENDING_VALIDATION,
    CONFIRMED,
    CANCELLED,
    COMPLETED,
    REJECTED_SALON,
    REJECTED_CLIENT,
    REJECTED_EMPLOYEE,
    REJECTED_SLOT,
    REJECTED_SERVICE,
}
