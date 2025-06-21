package com.taass.salon_service.controller;

import com.taass.salon_service.model.TimeSlot;
import com.taass.salon_service.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    // Create a new TimeSlot
    @PostMapping
    public ResponseEntity<TimeSlot> createTimeSlot(@RequestBody TimeSlot timeSlot) {
        TimeSlot createdTimeSlot = timeSlotService.createTimeSlot(timeSlot);
        return ResponseEntity.ok(createdTimeSlot);
    }

    // Get all TimeSlots
    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlots();
        return ResponseEntity.ok(timeSlots);
    }

    // Get a specific TimeSlot by ID
    @GetMapping("/{id}")
    public ResponseEntity<TimeSlot> getTimeSlotById(@PathVariable Long id) {
        Optional<TimeSlot> timeSlot = timeSlotService.getTimeSlotById(id);
        return timeSlot.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a TimeSlot
    @PutMapping("/{id}")
    public ResponseEntity<TimeSlot> updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlot updatedTimeSlot) {
        try {
            TimeSlot timeSlot = timeSlotService.updateTimeSlot(id, updatedTimeSlot);
            return ResponseEntity.ok(timeSlot);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a TimeSlot
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) {
        try {
            timeSlotService.deleteTimeSlot(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Book a TimeSlot
    @PostMapping("/{id}/book")
    public ResponseEntity<TimeSlot> bookTimeSlot(@PathVariable Long id) {
        try {
            TimeSlot bookedTimeSlot = timeSlotService.bookTimeSlot(id);
            return ResponseEntity.ok(bookedTimeSlot);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}