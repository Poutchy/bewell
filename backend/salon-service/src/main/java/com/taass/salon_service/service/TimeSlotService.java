package com.taass.salon_service.service;

import com.taass.salon_service.exception.TimeSlotNotFoundException;
import com.taass.salon_service.model.TimeSlot;
import com.taass.salon_service.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    // Create a new TimeSlot
    public TimeSlot createTimeSlot(TimeSlot timeSlot) {
        logger.info("Creating new TimeSlot: " + timeSlot);
        return timeSlotRepository.save(timeSlot);
    }

    // Retrieve all TimeSlots
    public List<TimeSlot> getAllTimeSlots() {
        logger.info("Retrieving all TimeSlots");
        return timeSlotRepository.findAll();
    }

    // Retrieve a TimeSlot by ID
    public Optional<TimeSlot> getTimeSlotById(Long id) {
        logger.info("Retrieving TimeSlot with ID: " + id);
        return timeSlotRepository.findById(id);
    }

    // Update an existing TimeSlot
    public TimeSlot updateTimeSlot(Long id, TimeSlot updatedTimeSlot) {
        logger.info("Updating TimeSlot with ID: " + id);
        return timeSlotRepository.findById(id)
                .map(existingTimeSlot -> {
                    existingTimeSlot.setStartTime(updatedTimeSlot.getStartTime());
                    existingTimeSlot.setEndTime(updatedTimeSlot.getEndTime());
                    existingTimeSlot.setAvailable(updatedTimeSlot.isAvailable());
                    return timeSlotRepository.save(existingTimeSlot);
                })
                .orElseThrow(() -> new TimeSlotNotFoundException(id.toString()));
    }

    // Delete a TimeSlot
    public void deleteTimeSlot(Long id) {
        logger.info("Deleting TimeSlot with ID: " + id);
        if (!timeSlotRepository.existsById(id)) {
            throw new IllegalArgumentException("TimeSlot with ID " + id + " not found");
        }
        timeSlotRepository.deleteById(id);
    }

    // Book a TimeSlot
    public TimeSlot bookTimeSlot(Long id) {
        logger.info("Booking TimeSlot with ID: " + id);
        return timeSlotRepository.findById(id)
                .map(timeSlot -> {
                    if (!timeSlot.isAvailable()) {
                        throw new TimeSlotNotFoundException(id.toString());
                    }
                    timeSlot.setAvailable(false);
                    return timeSlotRepository.save(timeSlot);
                })
                .orElseThrow(() -> new TimeSlotNotFoundException(id.toString()));
    }
}