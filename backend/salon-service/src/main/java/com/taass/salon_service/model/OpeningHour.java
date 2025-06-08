package com.taass.salon_service.model;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class OpeningHour {
    private Long id;
    private DayOfWeek day;
    private LocalTime open;
    private LocalTime close;
}
